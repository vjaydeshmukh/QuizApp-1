package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AnswersActivity extends AppCompatActivity {
    public static final String TAG = "AnswersActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Question> anslist;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        QuizApplication quizA = new QuizApplication();
        QuizPlay currentGame = quizA.getCurrentQuiz();

        //show correct answers on screen
        recyclerView = findViewById(R.id.recycler_view_ans);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        anslist = currentGame.getQuestions();
        adapter = new RecyclerViewAdapter(this, anslist);
        recyclerView.setAdapter(adapter);
    }

    public void onClickBack(View view) {
        finish();
    }
}
