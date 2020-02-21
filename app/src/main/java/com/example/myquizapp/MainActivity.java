package com.example.myquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemClickListener.OnRecyclerClickListener {
    private DrawerLayout drawer;
    public static final String TAG = "MainActivity";
    private List<Quiz> quizList;
    private int topicsTotalCount;
    private RecyclerView recyclerView;
    private RecyclerTopicsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String email;
    private static String uname;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");

        //Receive intent from login screen
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("Email_id");
        uname = (String) intent.getSerializableExtra("Name");
        Log.d(TAG, "onCreate: received email:" + email);
        Log.d(TAG, "onCreate: received username:" + uname);

        Toolbar toolbar = findViewById(R.id.toolbar);

        //set username email id on navigation header
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView username = (TextView) headerview.findViewById(R.id.uname);
        TextView emailid = (TextView) headerview.findViewById(R.id.email);

        username.setText(uname);
        emailid.setText(email);

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Retrieve data from quiz table to display on recycler
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        quizList = dbHelper.getAllQTopics();
        Log.d(TAG, "onCreate: quizLIst" + quizList.get(0));
        topicsTotalCount = quizList.size(); // GET number of questions


        recyclerView = findViewById(R.id.recyclerView_category);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //create instance of RecyclerItemClickListener and add it as touch listener to recycler view
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
        adapter = new RecyclerTopicsAdapter(this, quizList);
        recyclerView.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    //on selection of category move to next screen to display related question
    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts-normalmode");
        Intent intent = new Intent(this, QuizActivity.class);
        Quiz quizItem = adapter.getQuizList(position);
        String QID = quizItem.getQid();
        intent.putExtra("QUIZ_TOPIC", QID);
        intent.putExtra("EMAIL", email);
        intent.putExtra("USER_NAME", uname);
        startActivity(intent);

    }

    //menu item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_about:


                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_help:

                Intent intent2 = new Intent(this, ActivityHelp.class);
                startActivity(intent2);

                break;
            case R.id.nav_exit:
                Intent intent3 = new Intent(this, Login.class);
                startActivity(intent3);

                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
