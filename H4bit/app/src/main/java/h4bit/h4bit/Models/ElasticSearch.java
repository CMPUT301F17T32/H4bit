package h4bit.h4bit.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import h4bit.h4bit.Controllers.ElasticSearchController;

/** ElasticSearch class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 *
 * Class that uses ElasticSearchController to add/get classes, habits etc
 */

public class ElasticSearch {

    private ArrayList<Habit> userHabitList = new ArrayList<Habit>();
    public ElasticSearch(){}

    /**
     * Add a user to elastic search database
     * return true if user was added, false if not
     * @param user The user object
     * @return boolean
     */
    public boolean addUser(User user) {
        ElasticSearchController.AddUsersTask addUsersTask = new ElasticSearchController.AddUsersTask();
        addUsersTask.execute(user);
        try {
            addUsersTask.get();
            return true;
        }
        catch (Exception e) {
            Log.i("Error", "Failed to add user");
            return false;
        }
    }

    public boolean updateUser(User user) {
        user.setLastModified(new Date());
        ElasticSearchController.UpdateUserTask updateUserTask = new ElasticSearchController.UpdateUserTask();
        user.setLastModified(new Date());
        updateUserTask.execute(user);
        try {
            user.setLastModified(new Date());
            updateUserTask.get();
            return true;
        }
        catch (Exception e) {
            Log.i("Error", "Failed to update user");
            return false;
        }
    }

    /**
     *
     * @param username
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public User getUser(String username) throws ExecutionException, InterruptedException {
        ElasticSearchController.GetUsersTask getUsersTask = new ElasticSearchController.GetUsersTask();
        getUsersTask.execute(username);
        return getUsersTask.get();
    }
}
