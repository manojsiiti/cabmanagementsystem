package ratelimit;

public class Request {
    private String serviceName;
    private String apiName;
    private RequestType requestType;
    private long currentTimeInMiliSec;

    public Request(String serviceName, String apiName, RequestType requestType, long currentTimeInMiliSec) {
        this.serviceName = serviceName;
        this.apiName = apiName;
        this.requestType = requestType;
        this.currentTimeInMiliSec = currentTimeInMiliSec;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public long getCurrentTimeInMiliSec() {
        return currentTimeInMiliSec;
    }

    public void setCurrentTimeInMiliSec(long currentTimeInMiliSec) {
        this.currentTimeInMiliSec = currentTimeInMiliSec;
    }
}
