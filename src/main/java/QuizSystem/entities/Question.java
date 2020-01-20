package QuizSystem.entities;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;

    public Question() {
    }

    public Question(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String toString() {
        return "Question is: "+ this.question+"\n Options are: "+options.toString()+"\n";
    }
}
