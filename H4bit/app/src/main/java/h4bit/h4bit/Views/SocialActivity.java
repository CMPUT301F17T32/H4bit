package h4bit.h4bit.Views;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.ElasticSearch;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;
import io.searchbox.core.Index;

/**
 * SocialActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class SocialActivity extends FragmentActivity implements FollowUserDialog.OnCompleteListener, HandleRequestDialog.onDismissListener {

    private User user;
    private String savefile;
    private StatusAdapter statusAdapter;
    private FragmentManager fm = getFragmentManager();
    private SaveLoadController saveLoadController;
    private ElasticSearch elasticSearch = new ElasticSearch();
    private Button requestButton;

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
        requestButton = (Button) findViewById(R.id.requestsButton);
        socialButton.setPressed(true);
        socialButton.setEnabled(false);
        statusAdapter = new StatusAdapter(this, user.getFollowing(),savefile);
        ListView listView = (ListView) findViewById(R.id.habitStatusList);
        listView.setAdapter(statusAdapter);

        Log.d("requestcount", String.valueOf(user.getRequests().size()));
        if(user.getRequests().size() > 0){
            requestButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
        } else {
            requestButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.CLEAR);
        }

        requestButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                try{
                    HandleRequestDialog hrd = new HandleRequestDialog().newInstance(user.getRequests().get(0));
                    hrd.show(fm, "request");
                } catch (IndexOutOfBoundsException e){
                    Log.d("no requests", "none");
                }
            }
        });

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
                //String socialEventList = new Gson().toJson(socialEventList);
                //intent.putExtra("socialEventList", socialEventList);
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
        try {
            User followRecipient = elasticSearch.getUser(username);
            if (followRecipient.getUsername().equals(username)){
                // Check if request already sent
                if(followRecipient.getRequests().contains(user.getUsername())){
                    Log.d("SocialActivity", "Request already sent to that user!");
                    Toast.makeText(SocialActivity.this, "Request already sent to: " + username, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(followRecipient.getFollowers().contains(user.getUsername())){
                    Log.d("SocialActivity", "You are already following that person!");
                    Toast.makeText(SocialActivity.this, "Already following: " + username, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if already following
                Toast.makeText(SocialActivity.this, "Follow Request Sent to " + username, Toast.LENGTH_SHORT).show();
                followRecipient.addRequests(user.getUsername());
                elasticSearch.updateUser(followRecipient);
            }
        } catch (Exception e) {
            Toast.makeText(SocialActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDismiss(boolean b, String username){
        try {
            User acceptRecipient = elasticSearch.getUser(username);
            if (acceptRecipient.getUsername().equals(username)) {
                if(b){
                    Toast.makeText(SocialActivity.this, username + " has been allowed to follow you", Toast.LENGTH_SHORT).show();
                    acceptRecipient.addFollowing(user.getUsername());
                    user.addFollower(acceptRecipient.getUsername());
                    user.removeRequests(acceptRecipient.getUsername());
                    elasticSearch.updateUser(user);
                    elasticSearch.updateUser(acceptRecipient);
                } else {
                    Toast.makeText(SocialActivity.this, username + "'s request has been ignored", Toast.LENGTH_SHORT).show();
                    user.removeRequests(acceptRecipient.getUsername());
                    elasticSearch.updateUser(user);
                }
            }
        } catch (Exception e) {
            Toast.makeText(SocialActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
        }
        if(user.getRequests().size() > 0){
            requestButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
        } else {
            requestButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.CLEAR);
        }
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
