package QuizSystem.service;

import QuizSystem.entities.Question;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizData {

    private static Map<String, String> qaMap = new HashMap<String, String>();
    private static int scorePerQuestion = 5;
    private static Map<String, Integer> userScoreMap = new HashMap<>();

    public void fillAnswers() {

        qaMap.put("q1","A|B|C");
        qaMap.put("q2","D|A");
        qaMap.put("q3","D");
        qaMap.put("q4","B");
        qaMap.put("q5","B|C");
        qaMap.put("q6","C");
        qaMap.put("q7","A");
        qaMap.put("q8","D|B");
        qaMap.put("q9","B");
        qaMap.put("q10","A|D");
    }

    public List<Question> getAllQuestion() {
        List<String> options = Arrays.asList("A","B","C","D");
        Question q1 = new Question("q1", options);
        Question q2 = new Question("q2", options);
        Question q3 = new Question("q3", options);
        Question q4 = new Question("q5", options);
        Question q5 = new Question("q4", options);
        Question q6 = new Question("q6", options);
        Question q7 = new Question("q7", options);
        Question q8 = new Question("q8", options);
        Question q9 = new Question("q9", options);
        Question q10 = new Question("q10", options);
        return Arrays.asList(q1,q2,q3,q4,q5,q6,q7,q8,q9,q10);
    }

    public int submitQuiz(Map<String, String> answers, String userName, boolean allCorrect) {
        if(StringUtils.isEmpty(userName)) {
            throw new RuntimeException("Invalid User");
        }
        if(userScoreMap.containsKey(userName)) {
            throw new RuntimeException("You have already submitted the quiz");
        }
        int score = calculateScore(answers, allCorrect);
        userScoreMap.put(userName, score);
        return score;
    }

    public int getMyScore(String userName) {
        if(!userScoreMap.containsKey(userName)) {
            throw new RuntimeException("You haven't submitted the quiz yet.");
        }
        return userScoreMap.get(userName);
    }

    private int calculateScore(Map<String, String> userQAMap, boolean allCorrect) {
        int score = 0;
        for(String q : userQAMap.keySet()){
            String userAnswer = userQAMap.get(q);
            String[] correctAnswers = qaMap.get(q).split("|");

            //Case where only one correct option is needed to get the credit
            if(!allCorrect) { // we don't need to pass all the correct answers
                for (String co : correctAnswers) {
                    if (co.equalsIgnoreCase(userAnswer)) {
                        score = score + scorePerQuestion;
                        break;
                    }
                }
            } else { // Case where all the correct option are needed to get the credit
                String[] userAnswers = userAnswer.split("|");

                // number of correct option should be equal to options select by User
                if(userAnswers.length==correctAnswers.length) {
                    boolean foundAll = true;
                    //check for each correct option if that was selected or not
                    for (String co : correctAnswers) {
                        boolean foundThis = false;
                        for(String ua : userAnswers) {
                            if(ua.equalsIgnoreCase(co)) {
                                foundThis = true;
                                break;
                            }
                        }
                        if(!foundThis){
                            foundAll = false;
                            break;
                        }
                    }
                    // if all the correct options are selected add the score
                    if(foundAll) {
                        score = score + scorePerQuestion;
                    }
                }
            }
        }
        return  score;
    }

}
