package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class EndgameActivity extends AppCompatActivity {
    public static final String TAG = "EndgameActivity";
    private static String email;
    private static String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("EMAIL");
        uname = (String) intent.getSerializableExtra("USER_NAME");
        Log.d(TAG, "onCreate: received email:" + email);
        Log.d(TAG, "onCreate: received username:" + uname);

        Pie pie = AnyChart.pie();

        QuizApplication quizA = new QuizApplication();
        QuizPlay currentGame = quizA.getCurrentQuiz();
        Log.d(TAG, "onCreate: currentGame:" + currentGame);
        String result = "Your Score is " + currentGame.getRight() + "/"
                + currentGame.getNumRounds() + ".. ";
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Right", currentGame.getRight()));
        data.add(new ValueDataEntry("Wrong", currentGame.getWrong()));
        pie.data(data);

        //set pie chart
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);

        //display result
        Log.d(TAG, "onCreate: result:" + result);
        TextView results = (TextView) findViewById(R.id.endgameResult);
        results.setText(result);
    }

    //on pressing answers button move to next activity
    public void onClickAnswers(View view) {
        Log.d(TAG, "onClickAnswers: called ");

        Intent i = new Intent(this, AnswersActivity.class);
        startActivity(i);
    }

    public void onClickMenu(View view) {
        finish();
    }

    //on pressing statistics button move to next activity
    public void onStatisticsClickMenu(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("EMAIL", email);
        intent.putExtra("USER_NAME", uname);
        Log.d(TAG, "onStatisticsClickMenu: sending intent to statistics");
        startActivity(intent);
    }
}
