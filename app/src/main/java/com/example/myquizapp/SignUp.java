package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;


public class SignUp extends AppCompatActivity {

    private EditText m_firstName;
    private EditText m_lastName;
    private EditText m_Email;
    private EditText m_phoneNumber;
    private EditText m_Password;
    private EditText m_confirmPwd, m_security;
    private Button m_signup;

    public static String firstname = "";
    public static String lastname = "";
    public static String email = "";
    public static String phoneNumber = "";
    public static String passowrd = "";
    public static String confirmPassword = "";
    public static String securityhint = "";

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        m_firstName = (EditText) findViewById(R.id.Signup_FirstName_editText);
        m_lastName = (EditText) findViewById(R.id.Signup_FirstName_editText);
        m_Email = (EditText) findViewById(R.id.Signup_Email_editText);
        m_phoneNumber = (EditText) findViewById(R.id.signup_phone);
        m_Password = (EditText) findViewById(R.id.signup_password);
        m_confirmPwd = (EditText) findViewById(R.id.confirmpassword);
        m_security = (EditText) findViewById(R.id.securityhint);
        m_signup = (Button) findViewById(R.id.signup);
        //      validations API used
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(this, R.id.signup_password, regexPassword, R.string.pwderr);
        mAwesomeValidation.addValidation(this, R.id.confirmpassword, R.id.signup_password, R.string.cnfpwderr);
        mAwesomeValidation.addValidation(this, R.id.Signup_Email_editText, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerr);
        mAwesomeValidation.addValidation(this, R.id.signup_phone, RegexTemplate.TELEPHONE, R.string.phnerr);

        m_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {
                    firstname = m_firstName.getText().toString();
                    lastname = m_lastName.getText().toString();
                    email = m_Email.getText().toString();
                    phoneNumber = m_phoneNumber.getText().toString();
                    passowrd = m_Password.getText().toString();
                    confirmPassword = m_confirmPwd.getText().toString();
                    securityhint = m_security.getText().toString();


                    // validate input values
                    if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || passowrd.isEmpty() || confirmPassword.isEmpty() || securityhint.isEmpty()) {
                        showMessage("please enter  All  valid fields");

                    } else if (firstname.isEmpty()) {
                        showMessage("please enter first name");

                    } else if (lastname.isEmpty()) {
                        showMessage("please enter last name");

                    } else if (email.isEmpty()) {
                        showMessage("please enter email id");

                    } else if (!isEmailValid(email)) {
                        showMessage("please enter  valid email id");

                    } else if (phoneNumber.isEmpty()) {
                        showMessage("please enter valid phone number");

                    } else if (phoneNumber.length() < 10) {
                        showMessage("please enter valid phone number");

                    } else if (passowrd.isEmpty()) {
                        showMessage("please enter valid password of length 8");

                    } else if (passowrd.length() < 5) {
                        showMessage("please enter valid password of length 8");

                    } else if (!passowrd.equals(confirmPassword)) { // else if password and confirm password are not equal
                        m_Password.setText("");
                        m_confirmPwd.setText("");
                        showMessage("please enter both password same");

                    } else if (securityhint.isEmpty()) {
                        showMessage("please enter Security Hint");
                    } else {
                        QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                        Boolean mail = dbHelper.searchemail(email);
                        if (mail == true) {
                            showMessage("Account is already exists");

                        } else {
                            dbHelper.insertuserdetails(firstname, firstname, email, phoneNumber, passowrd, securityhint);

                            showMessage("New account has been created");
                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivity(i);
                        }
                    }


                }
            }
        });

        // initialize button for existing users to return to signin activity

        TextView signin = findViewById(R.id.Signup_login_textview);
        String login = "Already a member? Sign in!";
        SpannableString s1 = new SpannableString(login);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // when button is clicked, go back to signin activity
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        };
        s1.setSpan(clickableSpan1, 18, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signin.setText(s1);
        signin.setMovementMethod(LinkMovementMethod.getInstance());
    }


    // to show toast message
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    // validate Email
    public static boolean isEmailValid(String email) {
        // email regex definition
        String regExpn = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        Pattern numberPattern = Pattern.compile(regExpn);
        Matcher numberMatcher = numberPattern.matcher(email.toUpperCase());

        // return result of match
        return numberMatcher.matches();
    }
}