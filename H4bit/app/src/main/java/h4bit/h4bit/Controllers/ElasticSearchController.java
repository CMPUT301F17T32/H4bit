package h4bit.h4bit.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.User;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/** User class
 * version 1.0
 * 2017-11-06.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 * shelved for now
 */


/*
 * Taken from lab 5
 * Changed to fit our Habit App
 */
public class ElasticSearchController {
    private static JestDroidClient client;

    public static class AddUsersTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user: users) {
                Index index = new Index.Builder(user).index("cmput301f17t32_h4bit").type("User").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                    }
                    else {
                        Log.i("Error", "Elastic search was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    // This function is used for getting a user object from the database
    public static class GetUsersTask extends AsyncTask<String, Void, User> {
        // ... varargs, can pass arbitrary amount of arguments
        @Override
        protected User doInBackground(String... search_parameters) {
            verifySettings();
            User user = null;

            String query = "{\"query\": {\"match\": {\"username\":\"" + search_parameters[0] + "\" }}}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f17t32_h4bit")
                    .addType("User")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    user = result.getSourceAsObject(User.class);
                }
                else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }


            } catch (IOException e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return user;
        }
    }

    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users){
            verifySettings();
            for (User user: users) {
                Index index = new Index.Builder(user).index("cmput301f17t32_h4bit").type("User").id(user.getId()).build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setId(result.getId()); // I added this line, maybe this is what was missing?
                        Log.i("Success", "Update successful");
                    }
                    else {
                        Log.i("Error", "Elastic search was not able to update the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}