package h4bit.h4bit.Views;

/**
 * Created by benhl on 2017-10-29.
 */

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.MapFragment;

import java.util.HashSet;
import java.util.Set;

import h4bit.h4bit.Controllers.SaveLoadController;
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

public class HabitHistoryActivity extends FragmentActivity{

    private User user;
    private String savefile;
    private FragmentManager fragmentManager;
    private SaveLoadController saveLoadController;
    private MapFragment mapFragment;
    private Context context;
    protected HabitEventAdapter habitEventAdapter;
    protected HabitEventList habitEventList;


    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_habit_history);

        this.context = this.getApplicationContext();

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setPressed(true);
        historyButton.setEnabled(false);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
//        ToggleButton mapToggle = (ToggleButton) findViewById(R.id.mapToggle);
        ListView eventsList = (ListView) findViewById(R.id.eventsList);

        // Init the map fragment manager and the map fragment
//        callback = this;
//        // Does this need to be reinitialized in onResume?? I assume so
//        fragmentManager = getFragmentManager();
//        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync(callback);
//        fragmentManager.beginTransaction().hide(mapFragment).commit();




        // get savefile
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();
//        loadFromFile();

        //autocompletetextview
        final HabitEventList habitEventAutoList = user.getHabitEventList();
        HabitList HabitAutoList = user.getHabitList();
        String[] Comments1 = new String[habitEventAutoList.size()];
        final String[] Names = new String[HabitAutoList.getSize()];

        for (int i = 0; i<habitEventAutoList.size();i++){
          Comments1[i]= habitEventAutoList.get(i).getComment();
        }
        Set<String> setOfComments= new HashSet<>();
        for (String comment : Comments1){
            setOfComments.add(comment);
        }
        final String[] Comments = setOfComments.toArray(new String[setOfComments.size()]);



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

        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(context, HabitEventMapActivity.class);
                intent.putExtra("savefile", savefile);
                startActivity(intent);
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
            @Override
            public void onClick (final View view){
                //habitEventList.clearList();
                //user = saveLoadController.load();
                //habitEventList = user.getHabitEventList();
                //HabitEventList SavedHabitEventList = saveOriginalList(habitEventList);
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
                // so at thiss point it displays the search results, and this wehre i need to get my initial habitEventList. the stuff that crashes the app is commented out
                //habitEventList.clearList();
                //habitEventList = saveOriginalList(SavedHabitEventList);
                //Toast.makeText(getApplicationContext(), habitEventList.get(0).getHabit().getName(),
                //  Toast.LENGTH_LONG).show();

            }
        });


    }


//    @Override
//    public void onMapReady(GoogleMap map) {
//        // We need to use this addMarker to add all the habits with locations
//        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
//        // First we need to get all the habitEvents
//        // Then we need to iterate through them and add a marker for each event with a location
//        for (int i = 0; i < habitEventList.size(); i++) {
//            HabitEvent habitEvent = habitEventList.get(i);
//            if (habitEvent.getLocation() != null){
//                Location location = habitEvent.getLocation();
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
//            }
//        }
//    }

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
        // This function will query the user's habit history
        // Doesn't elastic search do this? Does this mean elasticsearch
        // should store a user object AND that users history seperately so it can
        // be easily queried?
        // AutoCompleteTextView AutoCompleteTextView = (AutoCompleteTextView)


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