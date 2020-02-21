package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    public static final String TAG = "AddQuestionActivity";
    private EditText m_question;
    private EditText m_option1;
    private EditText m_option2;
    private EditText m_option3;
    private EditText m_Answer;
    private Spinner m_Quizoption;
    private Button m_submit;
    private Button m_Exit;

    public static String quizoption = "";
    public static String question = "";
    public static String option1 = "";
    public static String option2 = "";
    public static String option3 = "";
    public static String Answer = "";
    private List<Quiz> quizList;
    String selectedQuizid;
    int selectedQid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        m_Quizoption = (Spinner) findViewById(R.id.spQuiz);
        m_question = (EditText) findViewById(R.id.txtQuestion);
        m_Answer = (EditText) findViewById(R.id.Answer);
        m_option1 = (EditText) findViewById(R.id.txtOption1);
        m_option2 = (EditText) findViewById(R.id.txtOption2);
        m_option3 = (EditText) findViewById(R.id.txtOption3);

        m_submit = (Button) findViewById(R.id.btnSubmit);
        m_Exit = (Button) findViewById(R.id.btnExit);

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
                        //addModelsOnSpinner2();
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //accept new question options from user
        m_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                question = m_question.getText().toString();
                option1 = m_option1.getText().toString();
                option2 = m_option2.getText().toString();
                option3 = m_option3.getText().toString();
                Answer = m_Answer.getText().toString();
                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || Answer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all valid fields", Toast.LENGTH_LONG).show();
                } else if (question.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter question field", Toast.LENGTH_LONG).show();
                } else if (option1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Option1 field", Toast.LENGTH_LONG).show();
                } else if (option2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Option2 field", Toast.LENGTH_LONG).show();
                } else if (option3.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Option3 field", Toast.LENGTH_LONG).show();
                } else if (Answer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter answer field", Toast.LENGTH_LONG).show();
                } else {

                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    dbHelper.insertData(selectedQid, question, Answer, option1, option2, option3);
                    Toast.makeText(getApplicationContext(), "New question added", Toast.LENGTH_LONG).show();
                    m_Answer.getText().clear();
                    m_question.getText().clear();
                    m_option1.getText().clear();
                    m_option2.getText().clear();
                    m_option3.getText().clear();
                }

            }
        });
        m_Exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(AddQuestionActivity.this, AdminPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
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
    // to get quiz id on dropdown selection

}


