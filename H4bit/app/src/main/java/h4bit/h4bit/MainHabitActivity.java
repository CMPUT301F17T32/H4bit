package h4bit.h4bit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Calendar;


/**
 * habitsButton, socialButton, historyButton, addButton, habitsList
 * I suggest the habits button do nothing because it will only take us to the
 * screen we are already on (this one)
 */
public class MainHabitActivity extends AppCompatActivity {

    // So we will store the serialized user, and upload it online
    // as well as store it locally
    private User user;
    private String savefile;

    // This is just a placeholder to see if I can figure out how to list everything again
    private ListView habitListView;
    private ArrayAdapter<Habit> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_habit);

        // get filename from intent which is required to save/load
        // This should probably be done in every activity
        this.savefile = getIntent().getStringExtra("savefile");

        // Loads the user
        loadFromFile();

        // Again, clumsy but serving a basic purpose mostly right now
        // This will display all the users habits, not the ones due today
        adapter = new ArrayAdapter<Habit>(this, android.R.layout.simple_list_item_1, user.getHabitList().getRawList());
        habitListView = (ListView) findViewById(R.id.habitsList);
        habitListView.setAdapter(adapter);

        // Initializing the buttons and shit
        Button historyButton = (Button) findViewById(R.id.historyButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);
        Button addButton = (Button) findViewById(R.id.addButton);

        // The habitsButton should do nothing on this screen
        // (Because it takes us to where we already are)

        // This is the listener for the historyButton press
        //
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                newHabit();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                historyTab();
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                socialTab();
            }
        });
    }

    public void historyTab(){
        // This should start an activity

        Intent intent = new Intent(this, HabitHistoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void socialTab(){
        Intent intent = new Intent(this, SocialActivity.class);
        startActivity(intent);
        finish();

    }

    // This should take us to the create habit activity
    public void newHabit() {
        Intent intent = new Intent(this, CreateHabitActivity.class);
        intent.putExtra("savefile",this.savefile);
        startActivity(intent);

        // So when we get to here a new habit was added to the users list and saved
        // do we have to reload the user from the save file then notify the adapter?
        // or will we have to reload the listview as well?
        loadFromFile();
        adapter.notifyDataSetChanged();
        // Do not finish, as the user is allowed to back out of creating a habit
        // TODO add backbutton to xml
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(savefile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
//            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            this.user = gson.fromJson(in, User.class);

        } catch (FileNotFoundException e) {
            user = new User("test");
        }
    }

    // This is the code from the lonelyTwitter lab exercise
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(savefile, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(this.user, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
