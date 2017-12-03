package h4bit.h4bit.Models;

import android.util.Log;

import h4bit.h4bit.Controllers.ElasticSearchController;

/** ElasticSearch class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

/**
 * Class that uses ElasticSearchController to add classes, and add habits etc in future
 */
public class ElasticSearch {
    public ElasticSearch(){}

    /**
     * Add a user to elastic search database
     * return true if user was added, false if not
     * @param user
     * @return
     */
    public Boolean addUser(User user) {
        ElasticSearchController.AddUsersTask addUserTask = new ElasticSearchController.AddUsersTask();
        addUserTask.execute(user);
        try {
            addUserTask.get();
            return true;
        }
        catch (Exception e) {
            Log.i("Error", "Failed to add");
            return false;
        }
    }
}
