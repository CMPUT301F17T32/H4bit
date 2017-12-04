package h4bit.h4bit.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.User;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/** ElasticSearchController
 * version 1.0
 * 2017-11-06.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */


/*
 * Taken from lab 5 lonelyTwitter
 * Changed to fit our Habit App
 */
public class ElasticSearchController {
    private static JestDroidClient client;

    // This function is used to add a new user to the elastic search database
    // returns a user object
    public static class AddUsersTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {
            verifySettings();
            User returnMe = null;
            for (User user: users) {
                Index index = new Index.Builder(user).index("cmput301f17t32_h4bit").type("User").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("AddUsersTask", result.getId());
                        user.setId(result.getId());
                        Log.d("AddUsersTask", "User "+ user.getUsername() + " " + user.getId() +" added");
                        returnMe = user;
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
            return returnMe;
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
                    user = (result.getSourceAsObject(User.class));
                    Log.d("ElasticSearchControllerGET", new Gson().toJson(user));

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

    //Function used for updating a user that exists in the database
    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users){
            verifySettings();
            for (User user: users) {
//                Log.d("UpdateUsersTask", user.getId());
                user.setLastModified(new Date()); //Try this for date?
                Index index = new Index.Builder(user).index("cmput301f17t32_h4bit").type("User").id(user.getId()).build();

                try {
                    client.execute(index);
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("Success", user.getId());
                        user.setId(result.getId()); // I added this line, maybe this is what was missing?
                        Log.i("Success", "Update successful");
                        Log.i("Success", new Gson().toJson(user));
                        Log.i("Success", result.getId());
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

    public static class DeleteUserTask extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                try {
                    DocumentResult result = client.execute(new Delete.Builder(user.getId())
                            .index("cmput301f17t32_h4bit")
                            .type("User")
                            .build());
                    if (result.isSucceeded()) {
                        Log.i("ESC.DeleteUserTask", "The user was delete successfully.");
                    } else {
                        Log.e("ESC.DeleteUserTask", "Failed to delete user.");
                    }
                } catch (Exception e) {
                    Log.e("ESC.UpdateUserTask", "Something went wrong when we tried to communicate with the elasticsearch server!");
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