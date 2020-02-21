package com.example.myquizapp;

import android.app.Application;

//used for making only one instance of quiz play
public class QuizApplication extends Application {
    private static QuizPlay currentQuiz;

    public static void setCurrentQuiz(QuizPlay currentQuiz1) {
        currentQuiz = currentQuiz1;
    }

    public static QuizPlay getCurrentQuiz() {
        return currentQuiz;
    }

}
