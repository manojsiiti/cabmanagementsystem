package librarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//this tells that it's spring boot application
public class LibraryApp {

    public static void main(String arg[]){
        SpringApplication.run(LibraryApp.class,arg);// to start the application
    }
}
