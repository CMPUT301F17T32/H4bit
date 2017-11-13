package h4bit.h4bit.Views;

/**
 * Created by benhl on 2017-10-29.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    protected HabitEventAdapter adapter2;
    //protected ArrayAdapter<String[]> adapter;
    protected HabitEventList habitEventArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        habitEventArrayList = user.getHabitEventList();
        String[] Names = new String[habitEventArrayList.size()];
        Names = habitEventArrayList.toArray(Names);

        //AutoCompleteTextView autoCompleteTextView =(AutoCompleteTextView) findViewById(autoCompleteTextView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Names);
        //autoCompleteTextView.setAdapter(adapter);
        adapter2 = new HabitEventAdapter(this, habitEventArrayList, savefile);
        eventsList.setAdapter(adapter2);

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
                //EditText searchNameText = (EditText) findViewById(R.id.searchNameText);
                EditText searchCommentText = (EditText) findViewById(R.id.searchCommentText);

                //String name = searchNameText.getText().toString();
                String comment = searchCommentText.getText().toString();

                //searchHistory(name, comment);

            }
        });
       // eventsList.setOnClickListener(new );


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
}