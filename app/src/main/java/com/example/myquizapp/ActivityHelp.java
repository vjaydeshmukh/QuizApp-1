package com.example.myquizapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityHelp extends AppCompatActivity {
    public static final String TAG = "ActivityHelp";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Log.d(TAG, "onCreate: starts");
    }
}
