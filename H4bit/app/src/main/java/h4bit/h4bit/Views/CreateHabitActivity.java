


package h4bit.h4bit.Views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Objects;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.ElasticSearch;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Controllers.HabitController;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * CreateHabitActivity
 * Version 1.0
 * November 9th 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class CreateHabitActivity extends AppCompatActivity {

    private User user;
    private String savefile;
    private HabitController habitController;
    private boolean[] schedule;
    private String mode;
    private int position;
    private SaveLoadController saveLoadController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        // Get the savefile name from intent
        this.savefile = getIntent().getStringExtra("savefile");

        // Get the mode for creating/editing
        this.mode = getIntent().getStringExtra("mode");

        // Init the saveLoadController
        this.saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());

        // load the user from the savefile
        // Make sure you use the more recent user object
        user = saveLoadController.load();
        // init the habit controller
        this.habitController = new HabitController();

        // Init the boolean schedule array
        this.schedule = new boolean[7];

        Button createButton = (Button) findViewById(R.id.createButton);

        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.sundayToggle);
        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.mondayToggle);
        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.tuesdayToggle);
        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.wednesdayToggle);
        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.thursdayToggle);
        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.fridaytoggle);
        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.saturdayToggle);

        final DatePicker startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        Date todayDate = new Date();
        startDatePicker.updateDate(todayDate.getYear() + 1900, todayDate.getMonth(), todayDate.getDate());
        startDatePicker.setMinDate(todayDate.getTime());
        // only get the position if you are in edit mode
        // Also change button to say save

        if (Objects.equals(this.mode, "edit")) {
            this.position = getIntent().getIntExtra("position", -1);
            createButton.setText(R.string.save);
            EditText nameText = (EditText) findViewById(R.id.nameText);
            EditText commentText = (EditText) findViewById(R.id.commentText);
            //EditText dateText = (EditText) findViewById(R.id.dateCalendar);
            HabitList habitList = user.getHabitList();
            Habit habit = habitList.getHabit(this.position);
            nameText.setText(habit.getName());
            commentText.setText(habit.getComment());
            startDatePicker.updateDate(habit.getStartDate().getYear() + 1900, habit.getStartDate().getMonth(), habit.getStartDate().getDate());

            if (habit.getSchedule()[0]) {
                sundayToggle.setChecked(true);
                schedule[0] = true;
            }

            if (habit.getSchedule()[1]) {
                mondayToggle.setChecked(true);
                schedule[1] = true;
            }
            if (habit.getSchedule()[2]) {
                tuesdayToggle.setChecked(true);
                schedule[2] = true;
            }
            if (habit.getSchedule()[3]) {
                wednesdayToggle.setChecked(true);
                schedule[3] = true;
            }

            if (habit.getSchedule()[4]) {
                thursdayToggle.setChecked(true);
                schedule[4] = true;
            }

            if (habit.getSchedule()[5]) {
                fridayToggle.setChecked(true);
                schedule[5] = true;
            }

            if (habit.getSchedule()[6]) {
                saturdayToggle.setChecked(true);
                schedule[6] = true;
            }   //please don't view this code

            //dateText.setText(String.valueOf(habit.getDate()));
        }


        // init delete button
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        if (Objects.equals(this.mode, "edit")) {
            this.position = getIntent().getIntExtra("position", -1);
            createButton.setText("Save");
        } else {
            deleteButton.setVisibility(View.GONE);
        }


        // This is what happens when you hit the create button at the bottom of the screen
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Objects.equals(mode, "create"))
                    createHabit(startDatePicker.getYear(), startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
                    // How will edit habit get the habit its trying to edit?
                else
                    editHabit(startDatePicker.getYear(), startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
            }
        });

        // This is what happens when you hit the delete button at the bottom of the screen
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Objects.equals(mode, "edit"))
                    deleteHabit();
            }
        });

        // This is the listener for each toggleable button
        toggleButton(sundayToggle, 0);
        toggleButton(mondayToggle, 1);
        toggleButton(tuesdayToggle, 2);
        toggleButton(wednesdayToggle, 3);
        toggleButton(thursdayToggle, 4);
        toggleButton(fridayToggle, 5);
        toggleButton(saturdayToggle, 6);
    }

    public void toggleButton(ToggleButton button, final Integer day) {
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                schedule[day] = isChecked;
            }
        });
    }

    // This looks ugly af but don't worry because it still deletes the habit
    public void deleteHabit() {
        for(int i = 0; i < user.getHabitEventList().size(); i++){
            if(user.getHabitEventList().get(i).getHabit().getName().equals(user.getHabitList().getHabit(this.position).getName())){
                user.getHabitEventList().deleteHabitEvent(user.getHabitEventList().get(i));
            }
        }
        this.user.getHabitList().deleteHabit(user.getHabitList().getHabit(this.position));
        saveLoadController.save(this.user);
        finish();
    }

    public void createHabit(int year, int month, int day) {
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        //EditText dateCalendar = (EditText) findViewById(R.id.dateCalendar);

        // This will create the habit object using the controller
        Habit habit = habitController.createHabit(nameText.getText().toString(), commentText.getText().toString(), this.schedule);
        habit.setStartDate(new Date(year - 1900, month, day), user.getHabitEventList());
        // If the habit constraints aren't met we could throw a toast notification here
        // We also won't finish the activity
        if (habit == null) {
            Toast.makeText(CreateHabitActivity.this, "Habit name is max 20 characters and comment max 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the valid habit to the user

        this.user.addHabit(habit);

        // Save the new user to the user save file
        saveLoadController.save(this.user);

        // Finish the activity and take us back to the main habit screen
        finish();
    }

    public void editHabit(int year, int month, int day) {
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        if (habitController.editHabit(user, user.getHabitList().getHabit(this.position), nameText.getText().toString(), commentText.getText().toString(), this.schedule, new Date(year - 1900, month, day)) == -1) {
            Toast.makeText(CreateHabitActivity.this, "Habit name is max 20 characters and comment max 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        // do nothing if edit returns -1
        saveLoadController.save(user);
        finish();
    }
}