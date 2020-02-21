package com.example.myquizapp;

public class Score {
    private String username;
    private String emailid;
    private String quizId;
    private String score;
    private String numQues;


    public Score() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNumQues() {
        return numQues;
    }

    public void setNumQues(String totalQ) {
        this.numQues = totalQ;
    }

}
