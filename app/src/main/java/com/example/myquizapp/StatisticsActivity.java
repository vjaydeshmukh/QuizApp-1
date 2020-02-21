package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myquizapp.QuizDbHelper;
import com.example.myquizapp.R;
import com.example.myquizapp.Score;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    public static final String TAG = "StatisticsActivity";
    private static String email;
    private static String uname;
    private BarChart mchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("EMAIL");
        uname = (String) intent.getSerializableExtra("USER_NAME");
        Log.d(TAG, "onCreate: received email:" + email);
        Log.d(TAG, "onCreate: received username:" + uname);

        mchart = (BarChart) findViewById(R.id.chart1);
        mchart.getDescription().setEnabled(false);

        setData();
        mchart.setFitBars(true);
    }

    private void setData() {
        Log.d(TAG, "setData: started");
        ArrayList<BarEntry> yVals = new ArrayList<>();

        //get scores from db
        QuizDbHelper dbHelper = new QuizDbHelper(getApplicationContext());
        Log.d(TAG, "setData: DB helper called");
        List<Score> scoreList = dbHelper.getScoreData(email);
        Log.d(TAG, "setData: returned from dbhelper:" + scoreList);
        for (int i = 0; i < scoreList.size(); i++) {
            Log.d(TAG, "setData: inside for loop");
            int temp1 = Integer.parseInt(scoreList.get(i).getQuizId());
            Log.d(TAG, "setData: QID:" + temp1);
            int temp = Integer.parseInt(scoreList.get(i).getScore());
            Log.d(TAG, "setData: score:" + temp);
            yVals.add(new BarEntry(i, temp));
        }

        //display it on leaderboard
        BarDataSet set = new BarDataSet(yVals, "Quiz Category");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);


        BarData data = new BarData(set);
        mchart.setData(data);
        mchart.invalidate();
        mchart.animateY(500);

    }
}
