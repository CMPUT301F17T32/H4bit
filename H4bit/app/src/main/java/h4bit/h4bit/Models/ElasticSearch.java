package h4bit.h4bit.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import h4bit.h4bit.Controllers.ElasticSearchController;

/**
 * ElasticSearch class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 *
 * Class that uses ElasticSearchController to add, get, and update user objects
 * @see ElasticSearchController
 */

public class ElasticSearch {

    public ElasticSearch(){}

    /**
     * Add a user to elastic search database
     * @param user object to be added to the database
     * @return user object added to the database
     */
    public User addUser(User user) {
        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
        try {
            return addUsersTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to add user");
            return null;
        }
    }

    public boolean deleteProfile(User user){
        if(user != null) {
            User usr = new User();
            try {
                usr = getUser(user.getUsername());
                ElasticSearchController.DeleteUserTask deleteProfileTask = new ElasticSearchController.DeleteUserTask();
                deleteProfileTask.execute(usr);
                try {
                    deleteProfileTask.get();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } catch (Exception e){
                Log.i("Error","No such profile exists in the database");
                return false;
            }
        }
        else{
            Log.i("Error", "Invalid Profile entered");
            return false;
        }
    }

    /**
     * This function takes a user object that exists in the database and updates it
     * @param user The user object to be updated in the database
     */
    public void updateUser(User user) {
        Log.d("ElasticSearch", "Updating online user with: "+new Gson().toJson(user));
        ElasticSearchController.UpdateUserTask updateUserTask = new ElasticSearchController.UpdateUserTask();
        user.setLastModified(new Date());
        updateUserTask.execute(user);
        try {
            user.setLastModified(new Date());
            updateUserTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to update user");
        }
    }

    /**
     * This function takes a username string and searches for it in the database and returns
     * the user being searched for if it exists
     * @param username string of the username to be searched
     * @return user object of the user if user exists in database
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public User getUser(String username) throws ExecutionException, InterruptedException {
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute(username);
        return getUsersTask.get();
    }
}