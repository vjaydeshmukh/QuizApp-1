package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "QuizActivity";
    private Question currentQ;
    private QuizPlay currentGame;
    private QuizPlay currentQuiz;
    private TextView textViewQuestionCount;
    private Button nextBtn;
    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private RadioGroup rbGroup;

    private int score;
    private boolean answered;
    private static String email;
    private static String uname;
    private static String qid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("QuizActivity", "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //accept data from previous screen
        Intent intent = getIntent();
        qid = (String) intent.getSerializableExtra("QUIZ_TOPIC");
        email = (String) intent.getSerializableExtra("EMAIL");
        uname = (String) intent.getSerializableExtra("USER_NAME");
        Log.d(TAG, "onCreate: received qid:" + qid);
        Log.d(TAG, "onCreate: received email:" + email);
        Log.d(TAG, "onCreate: received uname:" + uname);

        rbGroup = findViewById(R.id.group1);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        nextBtn = findViewById(R.id.nextBtn);

        //retrieve question from db and display randomly on screen
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getSelectedQuestions(qid);
        questionCountTotal = questionList.size(); // GET number of questions

        final QuizPlay quiz = new QuizPlay();
        quiz.setQuestions(questionList);
        quiz.setNumRounds(getNumQuestions());

        Collections.shuffle(questionList);  // shuffle questions

        QuizApplication QApp = new QuizApplication();
        QApp.setCurrentQuiz(quiz);
        currentGame = QApp.getCurrentQuiz();
        Log.d(TAG, "onCreate: currentGame:" + currentGame);

        setQuestions();

        //check this answer and display next question on button pressed
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!checkAnswer()) {
                    Toast.makeText(getApplicationContext(), "Select an answer",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("QuizActivity", "About to check if game is over");
                if (currentGame.isGameOver()) {

                    Log.d(TAG, "This game is over");

                    //insert data into score table
                    QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
                    int qidI = Integer.parseInt(qid);
                    int score = currentGame.getRight();
                    Log.d(TAG, "onClick: insert data into score table");
                    dbHelper.insertScoreData(uname, email, qidI, score, getNumQuestions());

                    //  Move to next screen
                    Intent i = new Intent(getApplicationContext(), EndgameActivity.class);
                    i.putExtra("EMAIL", email);
                    i.putExtra("USER_NAME", uname);
                    startActivity(i);
                    finishQuiz();

                } else {
                    Log.d("QuizActivity", "Time for a new question");
                    setQuestions();
//                    checkAnswer();

                }
            }

        });

    }

    private void setQuestions() {
        rbGroup.clearCheck();
        currentQ = currentGame.getNextQuestion();
        Log.d("QuizActivity", "setQuestions:currentQ: " + currentQ);
        // set the question text from current question
        String question = capitalise(currentQ.getQuestion());
        TextView qText = (TextView) findViewById(R.id.question);
        qText.setText(question);

        // set the available options
        List<String> answers = currentQ.getQuestionOptions();
        TextView option1 = (TextView) findViewById(R.id.answer1);
        option1.setText(capitalise(answers.get(0)));

        TextView option2 = (TextView) findViewById(R.id.answer2);
        option2.setText(capitalise(answers.get(1)));

        TextView option3 = (TextView) findViewById(R.id.answer3);
        option3.setText(capitalise(answers.get(2)));

        TextView option4 = (TextView) findViewById(R.id.answer4);
        option4.setText(capitalise(answers.get(3)));

        questionCounter++;
        textViewQuestionCount.setText("Question: " + questionCounter + "/" + currentGame.getNumRounds());
        answered = false;
    }

    public static String capitalise(String s) {
        if (s == null || s.length() == 0) return s;
        String s1 = s.substring(0, 1).toUpperCase() + s.substring(1);
        return s1;
    }

    //checks if answer is crrct or wrong
    private boolean checkAnswer() {
        answered = true;

        String answer = getSelectedAnswer();
        if (answer == null) {
            return false;
        } else {
            Log.d("QuizActivity", "checkAnswer: " + answer);
            Log.d(TAG, "checkAnswer: currentQ:" + currentQ);
            Log.d(TAG, "checkAnswer: database ans:" + currentQ.getOption4());
            if (currentQ.getOption4().equalsIgnoreCase(answer)) {
                currentGame.incrementRightAnswers();
            } else {
                currentGame.incrementWrongAnswers();
            }
            return true;

        }

    }

    private String getSelectedAnswer() {
        RadioButton c1 = (RadioButton) findViewById(R.id.answer1);
        RadioButton c2 = (RadioButton) findViewById(R.id.answer2);
        RadioButton c3 = (RadioButton) findViewById(R.id.answer3);
        RadioButton c4 = (RadioButton) findViewById(R.id.answer4);
        if (c1.isChecked()) {
            return c1.getText().toString();
        }
        if (c2.isChecked()) {
            return c2.getText().toString();
        }
        if (c3.isChecked()) {
            return c3.getText().toString();
        }
        if (c4.isChecked()) {
            return c4.getText().toString();
        }
        return null;
    }

    private void finishQuiz() {
        finish();
    }

    public void setCurrentQuiz(QuizPlay currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public QuizPlay getCurrentQuiz() {
        return currentQuiz;
    }

    private int getNumQuestions() {

        return 5;
    }
}
