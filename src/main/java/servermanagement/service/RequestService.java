package servermanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import servermanagement.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    private static List<Request> requestList = new ArrayList<>();
    private static Map<String , Thread> threadMap = new HashMap();
    public Response sleep(Request request) {
        if(null == request || StringUtils.isEmpty(request.getConnId()) || StringUtils.isEmpty(request.getTimeOut()) ) {
            throw new RuntimeException("Invalid Request");
        }
        request.setCurrentTime(""+System.currentTimeMillis());
        requestList.add(request);
        threadMap.put(request.getConnId(), Thread.currentThread());
        try {
            int waitTime = Integer.parseInt(request.getTimeOut());
            Thread.sleep(waitTime * 1000);
            requestList.remove(request);
        } catch (InterruptedException ex){
            requestList.remove(request);
            return new Response(Stat.Killed);
        }
        return new Response(Stat.OK);
    }

    public Response kill(Request killrequest){
        if(null == killrequest || StringUtils.isEmpty(killrequest.getConnId())) {
            throw new RuntimeException("Invalid Request");
        }
        boolean found = requestList.stream().filter(request -> request.equals(killrequest)).findFirst().isPresent();
        if(!found){
            return new Response(Stat.NotFound);
        } else {
            Thread thread = threadMap.get(killrequest.getConnId());
            thread.interrupt();
            return new Response(Stat.OK);
        }
    }


    public List<ResultRequest> status() {
        List<ResultRequest> requests = new ArrayList<>();
        for(Request request: requestList) {
            int timOut = Integer.parseInt(request.getTimeOut());
            long timeConsumedInSec = (System.currentTimeMillis() - Long.parseLong(request.getCurrentTime()))/1000;
            String timeRemaining = ""+(timOut - timeConsumedInSec);
            requests.add(new ResultRequest(request.getConnId(), timeRemaining));
        }
        return requests;
    }


    public void  waitForRequest(Request request) {
        int waitTime = Integer.parseInt(request.getTimeOut());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(waitTime*1000);
                    requestList.remove(request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }


}
