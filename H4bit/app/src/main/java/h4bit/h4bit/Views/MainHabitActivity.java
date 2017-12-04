package h4bit.h4bit.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * MainHabitActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

/*
 * habitsButton, socialButton, historyButton, addButton, habitsList
 * I suggest the habits button do nothing because it will only take us to the
 * screen we are already on (this one)
 */
public class MainHabitActivity extends AppCompatActivity {

    // So we will store the serialized user, and upload it online
    // as well as store it locally
    private User user;
    private String savefile;
    private SaveLoadController saveLoadController;
    private HabitList habitList;
    private HabitAdapter habitAdapter;
    private ListView listView;

    @Override
    protected void onStart(){//Bundle savedInstanceState) {
        super.onStart();//savedInstanceState);
        setContentView(R.layout.activity_main_habit);

        // get filename from intent which is required to save/load
        // This should probably be done in every activity
        this.savefile = getIntent().getStringExtra("savefile");

        // Loads the user
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // This will display all the users habits, not the ones due today
        // list adapter init
        habitList = user.getHabitList();
        habitList.sortByNextDate();
        habitAdapter = new HabitAdapter(this, habitList, savefile);
        listView = (ListView)findViewById(R.id.habitsList);
        listView.setAdapter(habitAdapter);
        for(int i = 0; i < habitList.getSize(); i++){
            habitList.getHabit(i).setDoneToday(user.getHabitEventList().isDoneToday(habitList.getHabit(i)));// THIS
            habitList.getHabit(i).updateStats(user.getHabitEventList());
        }
        user.getHabitList().sortByNextDate();
        habitAdapter.notifyDataSetChanged();
        saveLoadController.save(user);

        // Initialize buttons
        Button historyButton = (Button) findViewById(R.id.historyButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        habitsButton.setPressed(true);
        habitsButton.setEnabled(false);
        Button addButton = (Button) findViewById(R.id.addButton);

        // The habitsButton should do nothing on this screen
        // (Because it takes us to where we already are)


        // This is the listener for the historyButton press
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                newHabit();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                historyTab();
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                socialTab();
            }
        });
    }

    /* This takes us to the history tab */
    public void historyTab(){
        // This should start an activity

        Intent intent = new Intent(this, HabitHistoryActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }

    // Takes us to the social tab
    public void socialTab(){
        Intent intent = new Intent(this, SocialActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();

    }

    /* This will take us to the create habit activity */
    public void newHabit() {
        Intent intent = new Intent(this, CreateHabitActivity.class);
        intent.putExtra("savefile",this.savefile);
        intent.putExtra("mode", "create");
        startActivity(intent);

        user = saveLoadController.load();
        habitAdapter.notifyDataSetChanged();
        // Do not finish, as the user is allowed to back out of creating a habit
    }
}
