package h4bit.h4bit.Views;

/**
 * Created by benhl on 2017-10-29.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * HabitHistoryActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

/*
 * habitsButton, socialButton, historyButton, searchNameText, searchCommentText, searchButton, eventsList
 * We should create a tab interface maybe??
 */

public class HabitHistoryActivity extends FragmentActivity implements OnMapReadyCallback{

    private User user;
    private String savefile;
    private FragmentManager fragmentManager;
    private SaveLoadController saveLoadController;
    private MapFragment mapFragment;
    protected HabitEventAdapter habitEventAdapter;
    protected HabitEventList habitEventList;
    private OnMapReadyCallback callback;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_habit_history);

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        ToggleButton mapToggle = (ToggleButton) findViewById(R.id.mapToggle);
        ListView eventsList = (ListView) findViewById(R.id.eventsList);

        // Init the map fragment manager and the map fragment
        callback = this;
        // Does this need to be reinitialized in onResume?? I assume so
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(callback);
        fragmentManager.beginTransaction().hide(mapFragment).commit();


        // get savefile
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();
//        loadFromFile();

        //autocompletetextview
        //habitEventArrayList = user.getHabitEventList();
        //String[] Names = new String[habitEventArrayList.size()];
        //Names = habitEventArrayList.toArray(Names);

        //AutoCompleteTextView autoCompleteTextView =(AutoCompleteTextView) findViewById(autoCompleteTextView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Names);
        //autoCompleteTextView.setAdapter(adapter);
        habitEventList = user.getHabitEventList();
        habitEventAdapter = new HabitEventAdapter(this, habitEventList, savefile);
        eventsList.setAdapter(habitEventAdapter);
        habitEventList.sortByDate();
        habitEventAdapter.notifyDataSetChanged();
        saveLoadController.save(user);

        mapToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // This will turn location on our off, rather, clicking the button will
                // activate location and store it in habitEvent
                // and toggling it off will make that location NULL
                if (isChecked) {
                    // We want this to show the map, and display all the users habits
                    fragmentManager.beginTransaction().show(mapFragment).commit();
                }else{
                    // We want this to hide the map and return the screen to the normal, non-janky layout
                    fragmentManager.beginTransaction().hide(mapFragment).commit();
                }
            }});

        habitsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                habitsTab();
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                socialTab();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                // AutoCompleteTextView searchNameText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
                //EditText searchCommentText = (EditText) findViewById(R.id.searchCommentText);

                //String name = searchNameText.getText().toString();
                //String comment = searchCommentText.getText().toString();

                //searchHistory(name, comment);

            }
        });
    }

    // This should end the fragment when the activity is no longer on top
    // ie when edit button is pressed
//    @Override
//    public void onPause(){
//        super.onPause();
//        fragmentManager.beginTransaction().remove(mapFragment);
//    }
//
//    @Override
//    public void onStop(){
//        super.onStop();
//        fragmentManager.beginTransaction().remove(mapFragment);
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//
//        // Reinit the map so it doesn't blow anything up
//        // Unless we need to delete the map fragment on starting a new activity
//        fragmentManager = getFragmentManager();
//        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync(this);
//        fragmentManager.beginTransaction().hide(mapFragment).commit();
//    }

    @Override
    public void onMapReady(GoogleMap map) {
        // We need to use this addMarker to add all the habits with locations
        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        // First we need to get all the habitEvents
        // Then we need to iterate through them and add a marker for each event with a location
        for (int i = 0; i < habitEventList.size(); i++) {
            HabitEvent habitEvent = habitEventList.get(i);
            if (habitEvent.getLocation() != null){
                Location location = habitEvent.getLocation();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
            }
        }
    }

    /**
     *
     * @param name
     * @param comment
     */
    public void searchHistory(String name, String comment){


        // This function will query the user's habit history
        // Doesn't elastic search do this? Does this mean elasticsearch
        // should store a user object AND that users history seperately so it can
        // be easily queried?
        // AutoCompleteTextView AutoCompleteTextView = (AutoCompleteTextView)

    }

    // This takes us back to the habitsTab activity, should finish the current activity as
    // to not create a huge stacking stack of tab activites
    public void habitsTab(){
        Intent intent = new Intent(this, MainHabitActivity.class);
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
}