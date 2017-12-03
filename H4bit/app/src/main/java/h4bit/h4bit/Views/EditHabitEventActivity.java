package h4bit.h4bit.Views;



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
 * EditHabitEventActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 *
 * This is the view for editing a habit event
 */

public class EditHabitEventActivity extends AppCompatActivity {

    private User user;
    private int position;
    private HabitEvent theHabitEvent;
    private SaveLoadController saveLoadController;

    @Override
    protected void onStart() {
        String savefile;

        super.onStart();

        setContentView(R.layout.activity_edit_habit_event);
        savefile = getIntent().getStringExtra("savefile");
        this.position = getIntent().getIntExtra("position",position);

        // Init the saveload controller and assign user
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        this.theHabitEvent = user.getHabitEventList().get(position);

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

    /**
     * This is the method that changes the habit event's data, and
     * is triggered on button click
     */
    private void editHabitEvent(){

        EditText commentText = (EditText) findViewById(R.id.reasonText);
        theHabitEvent.setComment(commentText.getText().toString());
        saveLoadController.save(user);
        finish();

    }
}