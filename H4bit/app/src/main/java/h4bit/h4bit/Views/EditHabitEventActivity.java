package h4bit.h4bit.Views;

/**
 * Created by benhl on 2017-10-29.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import h4bit.h4bit.Controllers.HabitEventController;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * nameText, dateCalendar, reasonText, createButton, sundayButton, mondayButton etc.
 */

public class EditHabitEventActivity extends AppCompatActivity {

    private User user;
    private String savefile;
    private int position;
    private boolean[] schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit_event);
        this.savefile = getIntent().getStringExtra("savefile");
        this.position = getIntent().getIntExtra("position",position);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.sundayToggle);
        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.mondayToggle);
        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.tuesdayToggle);
        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.wednesdayToggle);
        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.thursdayToggle);
        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.fridaytoggle);
        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.saturdayToggle);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editHabitEvent();
            }

        });
    }

    private void editHabitEvent(){
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.reasonText);
        EditText dateCalendar = (EditText) findViewById(R.id.startCalendar);
        loadFromFile();
        HabitEventList habitEventList = user.getHabitEventList();



    }
    public void toggleButton(ToggleButton button, final Integer day){
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule[day] = isChecked;
                } else {
                    // The toggle is disabled
                    schedule[day] = isChecked;
                }
            }
        });
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
