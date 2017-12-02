package h4bit.h4bit.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

    // This is just a placeholder to see if I can figure out how to list everything again
    //private ArrayAdapter<Habit> adapter;
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
//        loadFromFile();

        // Again, clumsy but serving a basic purpose mostly right now
        // This will display all the users habits, not the ones due today

        // list adapter init
        habitList = user.getHabitList();
        habitList.sortByNextDate();
        habitAdapter = new HabitAdapter(this, habitList, savefile);
        listView = (ListView)findViewById(R.id.habitsList);
        listView.setAdapter(habitAdapter);
        for(int i = 0; i < habitList.getSize(); i++){
            habitList.getHabit(i).setDoneToday(user.getHabitEventList().isDoneToday(habitList.getHabit(i)));
            habitList.getHabit(i).updateStats(user.getHabitEventList());
        }
        user.getHabitList().sortByNextDate();
        habitAdapter.notifyDataSetChanged();
        saveLoadController.save(user);
//        saveInFile();

        /* adapter = new ArrayAdapter<Habit>(this, android.R.layout.simple_list_item_1, user.getHabitList().getRawList());
        habitListView = (ListView) findViewById(R.id.habitsList);
        habitListView.setAdapter(adapter); */

        // Initializing the buttons and shit
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

//    @Override
    /*protected void onResume(){

        super.onResume();
        loadFromFile();
        habitList = user.getHabitList();
        habitList.sortByNextDate();
        habitAdapter = new HabitAdapter(this, habitList, savefile);
        listView = (ListView)findViewById(R.id.habitsList);
        listView.setAdapter(habitAdapter);
        user.getHabitList().sortByNextDate();
        habitAdapter.notifyDataSetChanged();
        saveInFile();
    }*/

    /* This takes us to the history tab */
    public void historyTab(){
        // This should start an activity

        Intent intent = new Intent(this, HabitHistoryActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }

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

        // So when we get to here a new habit was added to the users list and saved
        // do we have to reload the user from the save file then notify the adapter?
        // or will we have to reload the listview as well?
        user = saveLoadController.load();
//        loadFromFile();
        habitAdapter.notifyDataSetChanged();
        // Do not finish, as the user is allowed to back out of creating a habit
        // TODO add backbutton to xml
    }
}
