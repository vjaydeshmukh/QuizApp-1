package com.example.myquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    public static final String TAG = "Login";
    private EditText userid, password;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userid = findViewById(R.id.txtUserId);
        password = findViewById(R.id.txtPassword);
        Login = findViewById(R.id.btnLogin);
        TextView textview = findViewById(R.id.txtsignup);
        String text = "Not a Member yet? Signup now";

        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // start signup activity when signup button is pressed
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        };
        ss.setSpan(clickableSpan1, 17, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(ss);
        textview.setMovementMethod(LinkMovementMethod.getInstance());

        // get reference to forgot password "button"

        TextView textview2 = findViewById(R.id.txtforgotpwd);
        String text1 = "Forget password?";

        // make ClickableSpan to handle clicks of forgot password "button"
        SpannableString s1 = new SpannableString(text1);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // start forgot password activity when button is pressed
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        };
        s1.setSpan(clickableSpan2, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview2.setText(s1);
        textview2.setMovementMethod(LinkMovementMethod.getInstance());

        // attach click listener to login button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get username and password inpyut values
                final String user = userid.getText().toString();
                String pass = password.getText().toString();


                // if username or password was not entered
                if (user.isEmpty() || pass.isEmpty()) {
                    // show error message
                    showMessage("please enter valid Email and password");
                } else if (user.equals("admin@gmail.com")) {
                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    String[] userpassword = dbHelper.searchPassword(user);
                    Intent i = new Intent(Login.this, AdminPage.class);
                    i.putExtra("Email_id", user);
                    i.putExtra("Name", userpassword[1]);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } else {
                    Log.d(TAG, "onCreate: inside user validation");
                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    String[] userpassword = dbHelper.searchPassword(user);
                    Log.d(TAG, "onCreate:" + userpassword[0]);
                    Log.d(TAG, "onCreate:" + userpassword[1]);
                    if (pass.equals(userpassword[0])) {
                        // put databse verification conditon here
                        userid.setText("");
                        password.setText("");
                        Intent i = new Intent(Login.this, MainActivity.class);
                        i.putExtra("Email_id", user);
                        i.putExtra("Name", userpassword[1]);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        showMessage("User name and Password doesn't match");
                    }
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static boolean isEmailValid(String email) {
        // email regex definition
        String regExpn = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        // user a Matcher to check if the provided string matches the email regex
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        // return result of match
        return matcher.matches();
    }
}
