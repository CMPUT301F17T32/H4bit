package h4bit.h4bit.Views;

/**
 * Created by benhl on 2017-10-29.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Objects;
import java.util.stream.Collectors;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.HabitList;
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

public class HabitHistoryActivity extends MainHabitActivity{

    private User user;
    private String savefile;
    private ListView eventsList;
    private SaveLoadController saveLoadController;
    protected HabitEventAdapter habitEventAdapter;
    protected HabitEventList habitEventList;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_habit_history);

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setPressed(true);
        historyButton.setEnabled(false);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        ListView eventsList = (ListView) findViewById(R.id.eventsList);




        // get savefile
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();
//        loadFromFile();

        //autocompletetextview
        HabitEventList habitEventAutoList = user.getHabitEventList();
        HabitList HabitAutoList = user.getHabitList();
        final String[] Comments = new String[habitEventAutoList.size()];
        final String[] Names = new String[HabitAutoList.getSize()];
        for (int i = 0; i<habitEventAutoList.size();i++){
          Comments[i]= habitEventAutoList.get(i).getComment();
        }
        for (int i =0;i<HabitAutoList.getSize();i++){
          Names[i] = HabitAutoList.getHabit(i).getName();
        }

        final AutoCompleteTextView autoCompleteTextView =(AutoCompleteTextView) findViewById(R.id.AutoCompleteName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Names);
        autoCompleteTextView.setAdapter(adapter);
        final AutoCompleteTextView autoCompleteTextView2 =(AutoCompleteTextView) findViewById(R.id.AutoCompleteComment);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Comments);
        autoCompleteTextView2.setAdapter(adapter2);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView2.setThreshold(1);
        habitEventList = user.getHabitEventList();
        habitEventAdapter = new HabitEventAdapter(this, habitEventList, savefile);
        eventsList.setAdapter(habitEventAdapter);
        habitEventList.sortByDate();
        habitEventAdapter.notifyDataSetChanged();
        saveLoadController.save(user);
//        saveInFile();

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
            @Override
            public void onClick ( View view){
                //habitEventList = user.getHabitEventList();
                HabitEventList SavedHabitEventList = saveOriginalList(habitEventList);
                String name = autoCompleteTextView.getEditableText().toString();
                String comment = autoCompleteTextView2.getEditableText().toString();
                HabitEventList FullHabitEventList = new HabitEventList();
                if ((name.length()>0)&&(comment.length()>0)){
                    searchHistoryFull(name,comment,FullHabitEventList);
                }
                else if (name.length()>0){
                    searchHistoryName(name,FullHabitEventList);
                }
                else if (comment.length()>0){
                    searchHistoryComment(comment, FullHabitEventList);
                }
                //searchHistory(name,FullHabitEventList);
                habitEventAdapter.notifyDataSetChanged();
                //TODO so at thiss point it displays the search results, and this wehre i need to get my initial habitEventList. the stuff that crashes the app is commented out
                //habitEventList.clearList();
                //habitEventList = saveOriginalList(SavedHabitEventList);
                //Toast.makeText(getApplicationContext(), habitEventList.get(0).getHabit().getName(),
                          //  Toast.LENGTH_LONG).show();

            }
        });


    }


    public HabitEventList saveOriginalList(HabitEventList habitEventList){
        HabitEventList savedHabitEventList = new HabitEventList();
        for (int i=0;i<habitEventList.size();i++) {
            savedHabitEventList.addHabitEvent(habitEventList.get(i));
        }
        return savedHabitEventList;
    }

    public void searchHistoryName(String name, HabitEventList FullHabitEventList){


        for (int i=0; i<habitEventList.size();i++){
            if (name.equals(habitEventList.get(i).getHabit().getName())){
                FullHabitEventList.addHabitEvent(habitEventList.get(i));
            }


        }
        habitEventList.clearList();
        for (int i=0;i<FullHabitEventList.size();i++){
            habitEventList.addHabitEvent(FullHabitEventList.get(i));
        }
        // This function will query the user's habit history
        // Doesn't elastic search do this? Does this mean elasticsearch
        // should store a user object AND that users history seperately so it can
        // be easily queried?
        // AutoCompleteTextView AutoCompleteTextView = (AutoCompleteTextView)

    }
    public void searchHistoryComment(String comment, HabitEventList FullHabitEventList) {


        for (int i = 0; i < habitEventList.size(); i++) {
            if (comment.equals(habitEventList.get(i).getComment())) {
                FullHabitEventList.addHabitEvent(habitEventList.get(i));
            }

        }
        habitEventList.clearList();
        for (int i = 0; i < FullHabitEventList.size(); i++) {
            habitEventList.addHabitEvent(FullHabitEventList.get(i));
        }
    }
    public void searchHistoryFull(String name, String comment, HabitEventList FullHabitEventList) {


        for (int i = 0; i < habitEventList.size(); i++) {
            if (comment.equals(habitEventList.get(i).getComment())&&name.equals(habitEventList.get(i).getHabit().getName())) {
                FullHabitEventList.addHabitEvent(habitEventList.get(i));
            }

        }
        habitEventList.clearList();
        for (int i = 0; i < FullHabitEventList.size(); i++) {
            habitEventList.addHabitEvent(FullHabitEventList.get(i));
        }
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