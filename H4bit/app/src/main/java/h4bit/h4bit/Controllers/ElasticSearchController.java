package h4bit.h4bit.Controllers;

import android.os.AsyncTask;
import android.util.Log;

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

    // TODO we need a function which adds tweets to elastic search
    public static class AddHabitsTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("cmput301f17t32_h4bit").type("Habit").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        habit.setId(result.getId());
                        Log.i("Success", "Habit added");
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habit");
                    }

                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }
            }
        return null;
        }
    }

    public static class GetHabitsTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Habit> habits = new ArrayList<Habit>();

            // TODO Build the query
            // sorted by most recent to oldest
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f17t32_h4bit")
                    .addType("Habit")
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<Habit> foundHabits = result.getSourceAsObjectList(Habit.class);
                    habits.addAll(foundHabits);
                } else {
                    Log.i("Error","Something went wrong when we tried to communicate with the server");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return habits;
        }
    }

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
            //for (User user: users) {
                // try this to modify last thing
            users[0].setLastModified(new Date());
            Index index = new Index.Builder(users[0]).index("cmput301f17t32_h4bit").type("User").id(users[0].getUsername()).build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    //user.setLastModified(new Date());
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
//            }
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