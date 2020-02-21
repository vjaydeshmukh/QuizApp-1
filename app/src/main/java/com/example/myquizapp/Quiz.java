package com.example.myquizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {
    private String quizId;
    private String quizTopic;
    private boolean complete;

    public Quiz() {
    }

    public Quiz(String quizTopic) {
        this.quizTopic = quizTopic;
    }

    public Quiz(String quizId, String quizTopic) {
        this.quizId = quizId;
        this.quizTopic = quizTopic;
    }

    public String getQid() {
        return quizId;
    }

    public void setQid(String quizId) {

        this.quizId = quizId;
    }

    public String getQuizTopic() {

        return quizTopic;
    }

    public void setQuizTopic(String quizTopic) {
        this.quizTopic = quizTopic;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}



