package h4bit.h4bit.Views;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * SocialActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class SocialActivity extends FragmentActivity implements FollowUserDialog.OnCompleteListener {

    private User user;
    private String savefile;
    private StatusAdapter statusAdapter;
    private FragmentManager fm = getFragmentManager();
    private SaveLoadController saveLoadController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        // get savefile and user
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
        Button mapButton2 = (Button) findViewById(R.id.mapButton2);
        Button followButton = (Button) findViewById(R.id.followButton);
        socialButton.setPressed(true);
        socialButton.setEnabled(false);
        statusAdapter = new StatusAdapter(this, user.getFollowing(),savefile);
        ListView listView = (ListView) findViewById(R.id.habitStatusList);
        listView.setAdapter(statusAdapter);

        followButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                FollowUserDialog fud = new FollowUserDialog();
                fud.show(fm, "follow");
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // Take us into the map activity
                Intent intent = new Intent(context, HabitEventMapActivity.class);
                intent.putExtra("savefile", savefile);
                intent.putExtra("mode", "social");
                startActivity(intent);
            }
        });

        mapButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // Take us into the map activity
                Intent intent = new Intent(context, HabitEventMapActivity.class);
                intent.putExtra("savefile", savefile);
                intent.putExtra("mode", "nearby");
                startActivity(intent);
            }
        });

        habitsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                habitsTab();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                historyTab();
            }
        });

    }

    /**
     *
     * @param username
     */
    public void onComplete(String username){
        Log.d("here it is", username);
        Toast.makeText(SocialActivity.this, "Follow Request Sent to " + username, Toast.LENGTH_SHORT).show();
    }

    public void habitsTab(){
        Intent intent = new Intent(this, MainHabitActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }

    public void historyTab(){
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        intent.putExtra("savefile", savefile);
        startActivity(intent);
        finish();
    }
}
