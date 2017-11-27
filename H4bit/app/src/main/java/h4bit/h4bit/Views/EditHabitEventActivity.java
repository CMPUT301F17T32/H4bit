package h4bit.h4bit.Views;

/**
 * EditHabitEventActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import h4bit.h4bit.Controllers.HabitEventController;
import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
/*
 * nameText, dateCalendar, reasonText, createButton, sundayButton, mondayButton etc.
 */

public class EditHabitEventActivity extends AppCompatActivity {

    private User user;
    private String savefile;
    private int position;
    private HabitEvent theHabitEvent;
    private HabitEventList habitEventList;
    private SaveLoadController saveLoadController;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_edit_habit_event);
        this.savefile = getIntent().getStringExtra("savefile");
        this.position = getIntent().getIntExtra("position",position);

        // Init the saveload controller and assign user
        saveLoadController = new SaveLoadController(this.savefile, this.getApplicationContext());
        user = saveLoadController.load();
        //loadFromFile();

        this.theHabitEvent = user.getHabitEventList().get(position);
        this.habitEventList = user.getHabitEventList();

        Button saveButton = (Button) findViewById(R.id.saveEventButton);
        Button deleteButton = (Button) findViewById(R.id.deleteEventButton);
        TextView nameText = (TextView) findViewById(R.id.habitEventNameText);
        EditText commentText = (EditText) findViewById(R.id.reasonText);
        TextView dateTextView = (TextView) findViewById(R.id.habitEventDate);

        nameText.setText(theHabitEvent.getHabit().getName());
        commentText.setText(theHabitEvent.getComment());
        dateTextView.setText(theHabitEvent.getDate().toString());

        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                user.getHabitEventList().deleteHabitEvent(user.getHabitEventList().get(position));
                saveLoadController.save(user);
                //saveInFile();
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editHabitEvent();
            }

        });
    }

    private void editHabitEvent(){

        EditText commentText = (EditText) findViewById(R.id.reasonText);
        theHabitEvent.setComment(commentText.getText().toString());
        saveLoadController.save(user);
        //saveInFile();
        finish();

    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(savefile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
//            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            this.user = gson.fromJson(in, User.class);

        } catch (FileNotFoundException e) {
            user = new User("test");
        }
    }

    // This is the code from the lonelyTwitter lab exercise
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(savefile, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(this.user, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}