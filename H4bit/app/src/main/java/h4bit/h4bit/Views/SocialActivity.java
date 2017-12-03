package h4bit.h4bit.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import h4bit.h4bit.Controllers.SaveLoadController;
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
    private StatusAdapter statusAdapter;
    private SaveLoadController saveLoadController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        // get savefile and user
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
        socialButton.setPressed(true);
        socialButton.setEnabled(false);
        statusAdapter = new StatusAdapter(this, user.getFollowing(),savefile);
        ListView listView = (ListView) findViewById(R.id.habitStatusList);
        listView.setAdapter(statusAdapter);

        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // Take us into the map activity
                Intent intent = new Intent(context, HabitEventMapActivity.class);
                intent.putExtra("savefile", savefile);
                intent.putExtra("mode", "social");
                startActivity(intent);
            }
        });

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
