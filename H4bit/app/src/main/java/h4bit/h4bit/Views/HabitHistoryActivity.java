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

import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * habitsButton, socialButton, historyButton, searchNameText, searchCommentText, searchButton, eventsList
 * We should create a tab interface maybe??
 */

public class HabitHistoryActivity extends MainHabitActivity{

    private User user;
    private String savefile;
    private ListView eventsList;
    protected HabitEventAdapter habitEventAdapter;
    protected HabitEventList habitEventList;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_habit_history);

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        ListView eventsList = (ListView) findViewById(R.id.eventsList);


        // get savefile
        this.savefile = getIntent().getStringExtra("savefile");
        loadFromFile();
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
        saveInFile();

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
    public void searchHistory(String name, String comment){


        // This function will query the user's habit history
        // Doesn't elastic search do this? Does this mean elasticsearch
        // should store a user object AND that users history seperately so it can
        // be easily quereied?
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
    //public void editHabitEvent(){
    //  Intent intent = new Intent(this, EditHabitEventActivity.class);
    //intent.putExtra("savefile", savefile);
    //intent.putExtra("position",pos)
    //startActivity(intent);
    //finish();

}