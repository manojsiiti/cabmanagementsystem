package servermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//this tells that it's spring boot application
public class ServerApp {

    public static void main(String arg[]){
        SpringApplication.run(ServerApp.class,arg);// to start the application
    }
}
