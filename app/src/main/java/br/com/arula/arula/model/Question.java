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
    private String resA;
    private String resB;
    private String resC;
    private String resD;

    public String getResA() {
        return resA;
    }

    public void setResA(String resA) {
        this.resA = resA;
    }

    public String getResB() {
        return resB;
    }

    public void setResB(String resB) {
        this.resB = resB;
    }

    public String getResC() {
        return resC;
    }

    public void setResC(String resC) {
        this.resC = resC;
    }

    public String getResD() {
        return resD;
    }

    public void setResD(String resD) {
        this.resD = resD;
    }

    public String getResE() {
        return resE;
    }

    public void setResE(String resE) {
        this.resE = resE;
    }

    private String resE;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Question() {}

    public Question(String question, String course, String resA, String resB, String resC, String resD, String resE, int correctAnswer) {
        this.Question = question;
        this.course = course;
        this.resA = resA;
        this.resB = resB;
        this.resC = resC;
        this.resD = resD;
        this.resE = resE;
        this.correctAnswer = correctAnswer;
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
