package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Admin_QuizCategory extends AppCompatActivity {

    private EditText m_quizcategory;
    private Button m_submit, m_exit;

    public static String quizcategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__quiz_category);
        m_quizcategory = (EditText) findViewById(R.id.txtCategory);
        m_submit = (Button) findViewById(R.id.btnSubmit);
        m_exit = (Button) findViewById(R.id.btnExit);

        //add new quiz category
        m_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                quizcategory = m_quizcategory.getText().toString();
                if (quizcategory.isEmpty()) {
                    // show error message
                    Toast.makeText(getApplicationContext(), "Please Enter category", Toast.LENGTH_LONG).show();
                } else {
                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    dbHelper.insertCategory(quizcategory);
                    Toast.makeText(getApplicationContext(), "New category added", Toast.LENGTH_LONG).show();
                    m_quizcategory.getText().clear();
                }
            }
        });
        m_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_QuizCategory.this, AdminPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}

