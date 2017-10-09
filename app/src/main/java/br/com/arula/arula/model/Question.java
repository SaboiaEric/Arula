package br.com.arula.arula.model;

import java.io.Serializable;

/**
 * Created by Rafael Felipe on 04/10/2017.
 */

public class Question implements Serializable {
    private Long Id;
    private String Name;
    private String Question;
    private String Answers;
    private int correctAnswer;

    public Question() {}

    public Question(String name) {
        this.Name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] toArray() {
        return Answers.split("-");
    }

    public void fromArray(String [] array) {
        Answers = "";
        for(String s : array)
            Answers += s + "-";
    }
}
