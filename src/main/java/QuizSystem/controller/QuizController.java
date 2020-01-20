package QuizSystem.controller;

import QuizSystem.service.QuizData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuizController {

    @Autowired
    private QuizData quizData;

    @RequestMapping(method = RequestMethod.POST, value = "/submit/{forUser}")
    public String submitQuiz(@RequestBody Map<String, String> answers, @PathVariable("forUser") String userName) {
        quizData.fillAnswers();
        return "Submitted successfully. Your Score is: "+quizData.submitQuiz(answers, userName, false);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/allcorrect/{forUser}")
    public String submitQuizAllCorrect(@RequestBody Map<String, String> answers, @PathVariable("forUser") String userName) {
        quizData.fillAnswers();
        return "Submitted successfully. Your Score is: "+quizData.submitQuiz(answers, userName,true);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myscore/{forUser}")
    public String getMyScore(@PathVariable("forUser") String userName) {
        return "Your Score is: "+quizData.getMyScore(userName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    public String getQuestions() {
        return "Here are all the question for this Quiz. Select Any correct option to get the Credit for that Question.\n"+quizData.getAllQuestion().toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/questions/allCorrect")
    public String getQuestionsAllCorrect() {
        return "Here are all the question for this Quiz. Select All correct option to get the Credit for that Question.\n"+quizData.getAllQuestion().toString();
    }

}
