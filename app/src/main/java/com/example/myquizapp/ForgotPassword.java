package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    private EditText m_email;
    private EditText m_security;
    private EditText m_newpass;
    private EditText m_confirmpass;
    private Button m_btnchange;

    public static String email = "";
    public static String security = "";
    public static String newpass = "";
    public static String confirmpass = "";
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        m_email = (EditText) findViewById(R.id.txtEmail);
        m_security = (EditText) findViewById(R.id.txtsecurityhint);
        m_newpass = (EditText) findViewById(R.id.txtNewPass);
        m_confirmpass = (EditText) findViewById(R.id.txtconfirmpass);
        m_btnchange = (Button) findViewById(R.id.btnChangePass);

        m_btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = m_email.getText().toString();
                security = m_security.getText().toString();
                newpass = m_newpass.getText().toString();
                confirmpass = m_confirmpass.getText().toString();
                if (email.isEmpty() || security.isEmpty() || newpass.isEmpty() || confirmpass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter all valid fields", Toast.LENGTH_LONG).show();
                } else if (newpass.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Passowrd length should be atleast 5", Toast.LENGTH_LONG).show();
                } else if (!newpass.equals(confirmpass)) {
                    m_newpass.setText("");
                    m_confirmpass.setText("");

                    Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG).show();
                } else {
                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    userList = dbHelper.getAllUser();
                    for (int i = 0; i < userList.size(); i++) {
                        if (email.equals(userList.get(i).getEmail())) {
                            Log.d("forgot", "onCreate: email matched");
                            if (security.equals(userList.get(i).getHint())) {
                                Log.d("forgot", "onCreate: hint matched");
                                {
                                    dbHelper.updatePassword(email, security, newpass);
                                    Toast.makeText(getApplicationContext(), "Password has been changed", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_LONG).show();
                            m_email.getText().clear();
                            m_security.getText().clear();
                            m_newpass.getText().clear();
                            m_email.getText().clear();
                            m_confirmpass.getText().clear();
                            m_email.getText().clear();

                        }
                    }
                }
            }
        });
    }
}
