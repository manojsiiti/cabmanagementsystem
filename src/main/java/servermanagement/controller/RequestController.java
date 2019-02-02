package servermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import servermanagement.entities.Request;
import servermanagement.entities.Response;
import servermanagement.entities.ResultRequest;
import servermanagement.service.RequestService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService = new RequestService();

    @RequestMapping(method = RequestMethod.GET, value = "/sleep")
    public Response sleep(@PathParam("timeout") String timeout, @PathParam("connid") String connid){
        return requestService.sleep(new Request(connid, timeout));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/serverstatus")
    public List<ResultRequest> serverstatus(){
        return requestService.status();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/kill")
    public Response kill(@RequestBody Request request){
        return requestService.kill(request);
    }
}
