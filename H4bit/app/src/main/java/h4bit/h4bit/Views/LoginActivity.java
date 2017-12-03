package h4bit.h4bit.Views;



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
 * usernameText, passwordText, signupButton, crealogButton
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        // These aren't functional yet so they will be hidden
        //signupButton.setVisibility(View.GONE);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        passwordText.setVisibility(View.GONE);

    }

    // This should log in the user and take us to the main todays habits screen screen
    public void login() throws ExecutionException, InterruptedException {

        // Get the entered username and password text
        // Its probably good practice to compare the users pass with a stored
        // one-way hash for information security

        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty()) {
            //Log.i("Login", "Please enter a username");
            Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
        } else if (username.matches("[a-zA-Z0-9]+")) {  // need + so it doesn't only compare 1 letter
            try {
                User user = elasticSearch.getUser(username);
                if (user.getUsername().equals(username)) {
                    Log.i("Login", "Username exists");
                    Intent intent = new Intent(this, MainHabitActivity.class);
                    intent.putExtra("savefile", username + ".sav");
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




    public void signup(){

        User user = new User();
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);

        String username = usernameText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
        } else if (username.matches("[a-zA-Z0-9]+")) {
            try {
              user = elasticSearch.getUser(username);
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Could not fetch", Toast.LENGTH_SHORT).show();
            }
            // if username to register does not exist in database
            if (user == null) {
                boolean userCreated = elasticSearch.addUser(new User(username));
                if (userCreated == true) {
                    Log.i("Register", "Successfully registered");
                    Intent intent = new Intent(this, MainHabitActivity.class);
                    intent.putExtra("savefile", username + ".sav");
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Register", "Failed to create account");
                    Toast.makeText(LoginActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i("Register", "User exists");
                Toast.makeText(LoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid username. Username can only contain letters and numbers", Toast.LENGTH_SHORT).show();
        }
    }
}

