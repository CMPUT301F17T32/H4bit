package h4bit.h4bit;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by jv on 11/6/17.
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
            // figure out how all this works
            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("t32").type("Habit").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        habit.setId(result.getId());
                    } else {
                        Log.i("Error","Elasticsearch was not able to add the mood");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the habits");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets tweets from elastic search
    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();

            // TODO Build the query
            Search search = new Search.Builder(search_parameters[0]) .addIndex("testing").addType("tweet").build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<NormalTweet> foundTweet = result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(foundTweet);
                } else {
                    Log.i("Error","Something went wrong when we tried to communicate with the server");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
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
