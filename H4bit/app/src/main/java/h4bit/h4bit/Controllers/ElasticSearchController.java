package h4bit.h4bit.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

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


/**
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
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the habit");
                    }

                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }
                // figure out how all this works
                /**for (Habit habit : habits) {
                 Index index = new Index.Builder(habit).index("t32").type("Habit").build();

                 try {
                 // where is the client?
                 DocumentResult result = client.execute(index);

                 if (result.isSucceeded()) {
                 habit.setId(result.getId());
                 } else {
                 Log.i("Error","Elasticsearch was not able to add the habit");
                 }

                 }
                 catch (Exception e) {
                 Log.i("Error", "The application failed to build and send the habits");
                 }

                 }**/
                //return null;
            }
        return null;
        }
    }

//    // TODO we need a function which gets tweets from elastic search
//    public static class GetHabitsTask extends AsyncTask<String, Void, ArrayList<Habit>> {
//        @Override
//        protected ArrayList<Habit> doInBackground(String... search_parameters) {
//            verifySettings();
//
//            ArrayList<Habit> habits = new ArrayList<Habit>();
//
//            // TODO Build the query
//            Search query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
//            Search search = new Search.Builder(query)
//                    .addIndex("t32_h4bit").addType("Habit").build();
//            try {
//                // TODO get the results of the query
//                SearchResult result = client.execute(search);
//
//                if (result.isSucceeded()) {
//                    List<Habit> foundHabit = result.getSourceAsObjectList(Habit.class);
//                    habits.addAll(foundHabit);
//                } else {
//                    Log.i("Error","Something went wrong when we tried to communicate with the server");
//                }
//            }
//            catch (Exception e) {
//                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
//            }
//
//            return habits;
//        }
//    }

//    public static class getUsersTask extends AsyncTask<String, Void, User> {
//
//        protected User doInBackground(String... search_parameters) {
//            verifySettings();
//            return user;
//        }
//    }


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