package parkingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//this tells that it's spring boot application
public class CarParkingApp {

    public static void main(String arg[]){
        SpringApplication.run(CarParkingApp.class,arg);// to start the application
    }
}
