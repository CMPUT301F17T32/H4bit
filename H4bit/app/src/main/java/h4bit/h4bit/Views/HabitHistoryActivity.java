package h4bit.h4bit.Views;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
 *
 * Shows habit history, list of habit events, is searchable
 */

public class HabitHistoryActivity extends FragmentActivity{

    private String savefile;
    private Context context;
    protected HabitEventAdapter habitEventAdapter;
    protected HabitEventList habitEventList;


    @Override
    protected void onStart() {
        User user;
        SaveLoadController saveLoadController;
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

        // get savefile
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();
//        loadFromFile();

        //autocompletetextview
        final HabitEventList habitEventAutoList = user.getHabitEventList();
        final HabitList HabitAutoList = user.getHabitList();
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
        final HabitEventList savedHabitEventList = saveOriginalList(habitEventList);
        habitEventAdapter.notifyDataSetChanged();
        saveLoadController.save(user);

        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(context, HabitEventMapActivity.class);
                intent.putExtra("savefile", savefile);
                intent.putExtra("mode", "history");
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
                habitEventList.clearList();
                for (int i = 0; i < savedHabitEventList.size(); i++) {
                    habitEventList.addHabitEvent(savedHabitEventList.get(i));
                }

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
                if (habitEventList.size()==0){
                      Toast.makeText(getApplicationContext(), "No Matches Found",
                      Toast.LENGTH_LONG).show();
                }
                habitEventAdapter.notifyDataSetChanged();
                // so at thiss point it displays the search results, and this wehre i need to get my initial habitEventList. the stuff that crashes the app is commented out
                //habitEventList.clearList();
                //habitEventList = saveOriginalList(SavedHabitEventList);
                //Toast.makeText(getApplicationContext(), habitEventList.get(0).getHabit().getName(),
                //  Toast.LENGTH_LONG).show();

            }
        });


    }

    /**
     * This saves the original, unsearched habit event list so it is easy to grab later
     * Getting around potential pointer issues
     * @param habitEventList The clean habit event list you are working with
     * @return Returns the clean habitEventList you are working with
     */
    public HabitEventList saveOriginalList(HabitEventList habitEventList){
        HabitEventList savedHabitEventList = new HabitEventList();
        for (int i=0;i<habitEventList.size();i++) {
            savedHabitEventList.addHabitEvent(habitEventList.get(i));
        }
        return savedHabitEventList;
    }

    /**
     * This searches the habitEventList by name
     * @param name The name you want to search by
     * @param FullHabitEventList This is the full, unsearched habitEventList
     */
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

    /**
     * This searches the habitEventHistory comment section
     * @param comment The comment to be searched
     * @param FullHabitEventList This is the fullHabitEvent list so it searches a clean unsearched list
     */
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

    /**
     * This Searches with both a name and a comment
     * @param name Name to be searched;
     * @param comment Comment to be searched
     * @param FullHabitEventList The clean unsearched habit event list
     */
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

    /**
     * This ends the activity and starts the habit activity
     */
    public void habitsTab(){
        Intent intent = new Intent(this, MainHabitActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }

    /**
     * This ends the activity and starts the social tab activity
     */
    public void socialTab(){
        Intent intent = new Intent(this, SocialActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }
}