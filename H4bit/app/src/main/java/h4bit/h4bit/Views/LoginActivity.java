package h4bit.h4bit.Views;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import h4bit.h4bit.Controllers.ElasticSearchController;
import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.ElasticSearch;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

/**
 * LoginActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

/*
 * usernameText, signupButton, crealogButton
 * These are the respective textboxes required to handle the information
 * we plan for this screen to have dual functionality, so the crealog button should
 * either show create or login depending on whether they are
 * creating an account or logging in with an existing account
 *
 * Should there be an offline mode button? Should it just skip
 * the login screen when someone is offline?
 */

public class LoginActivity extends AppCompatActivity {

    private User user;
    private ArrayList<User> userList;
    private ElasticSearch elasticSearch = new ElasticSearch();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.context = getApplicationContext();

        Button signupButton = (Button) findViewById(R.id.signupButton);
        Button crealogButton = (Button) findViewById(R.id.crealogButton);

        // This will be the listener for the signup button
        signupButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                // Signup the user, put them into the system
                signup();
            }
        });

        crealogButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                // Log the user in
                try {
                    login();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // This should log in the user and take us to the main todays habits screen screen

    /**
     * Attempts to login with username
     * checks if username exists in the database, if not, notifies that user does not exist
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void login() throws ExecutionException, InterruptedException {

        EditText usernameText = (EditText) findViewById(R.id.usernameText);

        String username = usernameText.getText().toString();


        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
        } else if (username.matches("[a-zA-Z0-9]+")) {  // need + so it doesn't only compare 1 letter
            try {
                user = elasticSearch.getUser(username);
                if (user.getUsername().equals(username)) {
                    Log.i("Login", "Username exists");
                    Intent intent = new Intent(this, MainHabitActivity.class);
                    intent.putExtra("savefile", username + ".sav");
                    // Test this
                    new SaveLoadController(username+".sav", context).save(user);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Login", "Can't find");
                }
            } catch (Exception e) {
                Log.i("Login", "Error Getting Profile");
                Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username. Username can only contain letters and numbers", Toast.LENGTH_SHORT).show();
        }
    }

    // This adds a user to the elastic search database only if the user doesn't already exist
    public void signup(){

        User user = new User();
        EditText usernameText = (EditText) findViewById(R.id.usernameText);

        String username = usernameText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
        } else if (username.matches("[a-zA-Z0-9]+")) {
            try {
                user = elasticSearch.getUser(username);
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Could not fetch", Toast.LENGTH_SHORT).show();
            }
            // if username to register does not exist in database, create new user
            if (user == null) {
                User newUser = elasticSearch.addUser(new User(username));
                if (newUser != null) {
                    Log.i("Register", "Successfully registered");
                    Intent intent = new Intent(this, MainHabitActivity.class);
                    intent.putExtra("savefile", username + ".sav");
                    // Save the newly created user!
                    new SaveLoadController(username+".sav", context).save(newUser);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Register", "Failed to create account");
                    Toast.makeText(LoginActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
                // username does exist in database
            } else {
                Log.i("Register", "User exists");
                Toast.makeText(LoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username. Username can only contain letters and numbers", Toast.LENGTH_SHORT).show();
        }
    }
}