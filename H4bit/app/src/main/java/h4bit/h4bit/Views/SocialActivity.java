package h4bit.h4bit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * SocialActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class SocialActivity extends AppCompatActivity {

    private User user;
    private String savefile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        // this can just hang because it isnt a goal for part4

        // get savefile and user
        this.savefile = getIntent().getStringExtra("savefile");

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);

        habitsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                habitsTab();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                historyTab();
            }
        });
    }

    public void habitsTab(){
        Intent intent = new Intent(this, MainHabitActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }

    public void historyTab(){
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }
}
