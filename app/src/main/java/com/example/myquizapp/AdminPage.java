package com.example.myquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button m_question, m_category, m_leaderboardl;
    private DrawerLayout drawer;
    NavigationView navigationView;
    private static String email;
    private static String uname;
    public static final String TAG = "AdminPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("AdminPage", "onCreate: admin_main");
        setContentView(R.layout.activity_admin_page_main);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("Email_id");
        uname = (String) intent.getSerializableExtra("Name");
        Log.d(TAG, "onCreate: received email:" + email);
        Log.d(TAG, "onCreate: received username:" + uname);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set username email id on navigation header
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView username = (TextView) headerview.findViewById(R.id.uname);
        TextView emailid = (TextView) headerview.findViewById(R.id.email);

        username.setText(uname);
        emailid.setText(email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //navigates to new screen on button click
        m_question = (Button) findViewById(R.id.btnAddQuestion);
        m_category = (Button) findViewById(R.id.btnQuizCategory);
        m_leaderboardl = (Button) findViewById(R.id.btnleaderboard);
        m_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminPage.this, AddQuestionActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

        });
        m_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminPage.this, Admin_QuizCategory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

        });
        m_leaderboardl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: leaderbord intent");
                Intent i = new Intent(AdminPage.this, Admin_Leaderboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

        });
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
}
