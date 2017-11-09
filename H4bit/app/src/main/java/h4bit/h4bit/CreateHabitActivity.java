package h4bit.h4bit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// createButton, nameText, commentText, mondayToggle, etc.
// test

public class CreateHabitActivity extends AppCompatActivity {

    private User user;
    private String savefile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        this.savefile = getIntent().getStringExtra("savefile");

        Button createButton = (Button) findViewById(R.id.createButton);


        createButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                createHabit();
            }
        });



    }

    // Bad, we need a habit controller for this!
    public void createHabit(){
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        EditText dateCalendar = (EditText) findViewById(R.id.dateCalendar);



        // TODO ensure habit information is valid
//        Habit habit = new Habit(nameText, commentText, //schedule)
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
