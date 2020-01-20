package QuizSystem;

import QuizSystem.service.QuizData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//this tells that it's spring boot application
public class QuizApp {

    public static void main(String arg[]){
        SpringApplication.run(QuizApp.class,arg);// to start the application

    }
}
