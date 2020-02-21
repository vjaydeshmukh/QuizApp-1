package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class Admin_Leaderboard extends AppCompatActivity {
    public static final String TAG = "Admin_Leaderboard";
    private Spinner m_Quizoption;
    private List<Quiz> quizList;
    private List<Score> scoreList;
    String selectedQuizid;
    int selectedQid;
    private RecyclerView recyclerView;
    private RecyclerAdminDashboard adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__leaderboard);
        m_Quizoption = (Spinner) findViewById(R.id.select_cat);
        loadSpinnerData();
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        quizList = dbHelper.getAllQTopics();

        m_Quizoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected1: starts");
                String spquiztopic = adapterView.getItemAtPosition(i).toString();
                for (int j = 0; j < quizList.size(); j++) {
                    if (quizList.get(i).getQuizTopic().equals(spquiztopic)) {
                        Log.d(TAG, "onItemSelected2: selected:" + quizList.get(i));
                        selectedQuizid = quizList.get(i).getQid();
                        selectedQid = Integer.parseInt(selectedQuizid);
                        Log.d(TAG, "onItemSelected3: makeid: " + selectedQid);
                        QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                        scoreList = dbHelper.getleaderboardscore(selectedQid);
                        Log.d(TAG, "onItemSelected: scorelist received size: " + scoreList.size());
                        recyclerView = findViewById(R.id.recyclerView_score);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new RecyclerAdminDashboard(getApplicationContext(), scoreList);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData() {
        QuizDbHelper db = new QuizDbHelper(getApplicationContext());
        List<String> labels = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        m_Quizoption.setAdapter(dataAdapter);
    }
}