package cabbooking.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//To make the class As RestController
public class HelloController {

    @RequestMapping("/hello")//when there is request with this url, this method will  be invoked
    public String sayHi(){
        return "Hi";
    }
}
