package ratelimit;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RateLimiter {

   //static String config = "{\"serviceLimits\":[{\"service\":\"OrderService\",\"globalLimits\":{\"GET\":{\"limit\":1,\"granularity\":\"second\"},\"POST\":{\"limit\":1,\"granularity\":\"minute\"}},\"apiLimits\":[{\"methods\":{\"GET\":{\"limit\":15,\"granularity\":\"second\"},\"POST\":{\"limit\":20,\"granularity\":\"minute\"}},\"api\":\"CreateOrder\"},{\"methods\":{\"GET\":{\"limit\":10,\"granularity\":\"second\"},\"POST\":{\"limit\":10,\"granularity\":\"second\"}},\"api\":\"GetOrderById\"}]},{\"service\":\"DeliveryService\",\"globalLimits\":{\"GET\":{\"limit\":10,\"granularity\":\"second\"},\"POST\":{\"limit\":20,\"granularity\":\"minute\"}},\"apiLimits\":[]}]}";
    static final String config = "{\"serviceLimits\":[{\"service\":\"OrderService\",\"globalLimits\":{\"GET\":{\"limit\":2,\"granularity\":\"second\"},\"POST\":{\"limit\":2,\"granularity\":\"minute\"}},\"apiLimits\":[{\"methods\":{\"GET\":{\"limit\":1,\"granularity\":\"second\"},\"POST\":{\"limit\":1,\"granularity\":\"minute\"}},\"api\":\"CreateOrder\"},{\"methods\":{\"GET\":{\"limit\":1,\"granularity\":\"second\"},\"POST\":{\"limit\":1,\"granularity\":\"second\"}},\"api\":\"GetOrderById\"}]},{\"service\":\"DeliveryService\",\"globalLimits\":{\"GET\":{\"limit\":10,\"granularity\":\"second\"},\"POST\":{\"limit\":20,\"granularity\":\"minute\"}},\"apiLimits\":[]}]}";
    static Config currentConfig =  new Config();
    static Config fixedConfig = null;
    static final long SECOND = 1000;
    static final long MINUTE = 60*1000;

    public static void main(String s[]) {

        Gson g = new Gson();
        fixedConfig = g.fromJson(config, Config.class);
        Scanner sc = new Scanner(System.in);

        while(true) {
            String serviceName = sc.next();
            String apiName = sc.next();
            String methodType = sc.next();

            Request request = new Request(serviceName, apiName, Enum.valueOf(RequestType.class, methodType),  System.currentTimeMillis());
            boolean result = submitRequest(request);
            System.out.println("Result :"+result);
        }
    }

    //Submit the request
    private static boolean submitRequest(Request request) {
        if(!validate(request)) {
            System.out.println("can't process request");
            return false;
        } else {
            processRequest(request);
            return true;
        }

    }

    //============ Process Method =====================

    //process the request
    private static void processRequest(Request request) {
        String serviceName = request.getServiceName();
        String apiName = request.getApiName();
        RequestType type  =request.getRequestType();

        ServiceLimit fixedServiceLimit = getServiceLimit(fixedConfig, serviceName);
        GlobalLimits fixedGlobalLimit = getGlobalLimit(fixedServiceLimit);

        ApiLimit fixedApiLimit = getApiLimit(fixedServiceLimit, apiName);

        ServiceLimit myServiceStatus = getServiceLimit(currentConfig, serviceName);

        GlobalLimits myGlobalStatus = getGlobalLimit(myServiceStatus);

        String getGranulForGlobal = getGranulForGlobal(fixedGlobalLimit, type);
        String getGranulForMethod = getGranulForMethod(fixedApiLimit, type);

        //Keep the count till it's time over and then reduce it, Fot Global Status
        Thread tGlobal = new Thread() {
            @Override
            public void run() {
                synchronized(myGlobalStatus) {
                    if (type == RequestType.GET) {
                        myGlobalStatus.getGET().setLimit(myGlobalStatus.getGET().getLimit() + 1);
                    } else {
                        myGlobalStatus.getPOST().setLimit(myGlobalStatus.getPOST().getLimit() + 1);
                    }
                }

                sleepIt(getGranulForGlobal);

                synchronized(myGlobalStatus) {
                    if (type == RequestType.GET) {
                        myGlobalStatus.getGET().setLimit(myGlobalStatus.getGET().getLimit() - 1);
                    } else {
                        myGlobalStatus.getPOST().setLimit(myGlobalStatus.getPOST().getLimit() - 1);
                    }
                }
            }
        };
        tGlobal.start();

        ApiLimit myApiLimitStatus = getApiLimit(myServiceStatus, apiName);

        //Keep the count till it's time over and then reduce it, For Api level Status
        Thread tMethod = new Thread() {
            @Override
            public void run() {
                synchronized(myApiLimitStatus) {
                    if (type == RequestType.GET) {
                        myApiLimitStatus.getMethods().getGET().setLimit(myApiLimitStatus.getMethods().getGET().getLimit() + 1);
                    } else {
                        myApiLimitStatus.getMethods().getPOST().setLimit(myApiLimitStatus.getMethods().getPOST().getLimit() + 1);
                    }
                }
                sleepIt(getGranulForMethod);

                synchronized(myApiLimitStatus) {
                    if (type == RequestType.GET) {
                        myApiLimitStatus.getMethods().getGET().setLimit(myApiLimitStatus.getMethods().getGET().getLimit() - 1);
                    } else {
                        myApiLimitStatus.getMethods().getPOST().setLimit(myApiLimitStatus.getMethods().getPOST().getLimit() - 1);
                    }
                }
            }
        };
        tMethod.start();
    }


    //======Validation Methods ====================

    //To validate if we can process the request or not
    private static boolean validate(Request request) {
        String serviceName = request.getServiceName();
        String apiName = request.getApiName();
        RequestType type  =request.getRequestType();

        ServiceLimit fixedServiceLimit = getServiceLimit(fixedConfig, serviceName);
        GlobalLimits fixedGlobalLimit = getGlobalLimit(fixedServiceLimit);

        ServiceLimit myServiceStatus = getServiceLimit(currentConfig, serviceName);

        //if it is not there, for First Request Validation
        if(myServiceStatus==null) {
            myServiceStatus = new ServiceLimit();
            myServiceStatus.setService(serviceName);

            List<ServiceLimit> serviceLimits = currentConfig.getServiceLimits();
            if(serviceLimits==null) {
                serviceLimits = new ArrayList<>();
            }
            serviceLimits.add(myServiceStatus);
            currentConfig.setServiceLimits(serviceLimits);
        }

        GlobalLimits myGlobalStatus = getGlobalLimit(myServiceStatus);

        if(!validateGlobalStatus(fixedGlobalLimit, myGlobalStatus, type)) {
            return false;
        }

        ApiLimit fixedApiLimit = getApiLimit(fixedServiceLimit, apiName);
        ApiLimit myApiLimitStatus = getApiLimit(myServiceStatus, apiName);

        //if it is not there, for First Request Validation
        if(myApiLimitStatus == null) {
            myApiLimitStatus = new ApiLimit();
            myApiLimitStatus.setApi(apiName);
            Methods methods = new Methods();
            myApiLimitStatus.setMethods(methods);
            List<ApiLimit> apiLimits = myServiceStatus.getApiLimits();
            if(apiLimits==null) {
                apiLimits = new ArrayList<>();
                myServiceStatus.setApiLimits(apiLimits);
            }
            apiLimits.add(myApiLimitStatus);
        }

        if(!validateApiLimit(fixedApiLimit, myApiLimitStatus, type)) {
            return false;
        }

        return true;
    }

    private static boolean validateApiLimit(ApiLimit fixedApiLimit,  ApiLimit myApiLimitStatus, RequestType type) {
        if(type == RequestType.GET) {
            return myApiLimitStatus.getMethods().getGET().getLimit() < fixedApiLimit.getMethods().getGET().getLimit();
        } else {
            return myApiLimitStatus.getMethods().getPOST().getLimit() < fixedApiLimit.getMethods().getPOST().getLimit();
        }
    }

    private static boolean  validateGlobalStatus(GlobalLimits fixedGlobalLimits,GlobalLimits globalLimits, RequestType type) {
        if(type == RequestType.GET ) {
            return globalLimits.getGET().getLimit() < fixedGlobalLimits.getGET().getLimit();
        } else {
            return globalLimits.getPOST().getLimit() < fixedGlobalLimits.getPOST().getLimit();
        }
    }

    //======= Utility Methods==========
    private static ServiceLimit getServiceLimit(Config config, String serviceName) {
        return config.getServiceLimits().stream().filter(serviceLimit -> serviceName.equalsIgnoreCase(serviceLimit.getService())).findFirst().orElse(null);
    }

    private static  GlobalLimits getGlobalLimit(ServiceLimit serviceLimit) {
        return serviceLimit.getGlobalLimits();
    }

    private static ApiLimit getApiLimit(ServiceLimit serviceLimit, String apiNmae) {
        return serviceLimit.getApiLimits().stream().filter(apiLimit -> apiNmae.equalsIgnoreCase(apiLimit.getApi())).findFirst().orElse(null);
    }

    private static void sleepIt(String g) {
        try {
            if("second".equalsIgnoreCase(g)) {
                Thread.sleep(SECOND);
            } else if ("minute".equalsIgnoreCase(g)) {
                Thread.sleep(MINUTE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getGranulForGlobal(GlobalLimits fixedGlobalLimit, RequestType type) {
        if(type==RequestType.GET) {
            return fixedGlobalLimit.getGET().getGranularity();
        } else {
            return fixedGlobalLimit.getPOST().getGranularity();
        }
    }

    private static String getGranulForMethod(ApiLimit apiLimit, RequestType type) {
        if(type==RequestType.GET) {
            return apiLimit.getMethods().getGET().getGranularity();
        } else {
            return apiLimit.getMethods().getPOST().getGranularity();
        }
    }
}
