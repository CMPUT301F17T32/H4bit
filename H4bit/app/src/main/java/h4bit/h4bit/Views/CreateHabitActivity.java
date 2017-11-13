package h4bit.h4bit.Views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.Objects;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Controllers.HabitController;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;
import h4bit.h4bit.Models.User;

// createButton, nameText, commentText, mondayToggle, etc.
// test

public class CreateHabitActivity extends AppCompatActivity {

    private User user;
    private String savefile;
    private HabitController habitController;
    private boolean[] schedule;
    private String mode;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        // Get the savefile name from intent
        this.savefile = getIntent().getStringExtra("savefile");

        // Get the mode for creating/editing
        this.mode = getIntent().getStringExtra("mode");

        // load the user from the savefile
        loadFromFile();

        // init the habit controller
        this.habitController = new HabitController();

        // Init the boolean schedule array
        this.schedule = new boolean[7];

        Button createButton = (Button) findViewById(R.id.createButton);

        // only get the position if you are in edit mode
        // Also change button to say save

        if (Objects.equals(this.mode, "edit")) {
            this.position = getIntent().getIntExtra("position", -1);
            createButton.setText(R.string.save);
        }

        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        EditText dateText = (EditText) findViewById(R.id.dateCalendar);
        HabitList habitList = user.getHabitList();
        Habit habit = habitList.getHabit(this.position);
        nameText.setText(habit.getName());
        commentText.setText(habit.getComment());
        //dateText.setText(String.valueOf(habit.getDate()));

        // init delete button
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        if (Objects.equals(this.mode, "edit")) {
            this.position = getIntent().getIntExtra("position", -1);
            createButton.setText("Save");
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.sundayToggle);
        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.mondayToggle);
        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.tuesdayToggle);
        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.wednesdayToggle);
        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.thursdayToggle);
        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.fridaytoggle);
        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.saturdayToggle);


        // This is what happens when you hit the create button at the bottom of the screen
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Objects.equals(mode, "create"))
                    createHabit();
                    // How will edit habit get the habit its trying to edit?
                else
                    editHabit();
            }
        });

        // This is what happens when you hit the delete button at the bottom of the screen
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Objects.equals(mode, "edit"))
                    deleteHabit();
            }
        });

        // This is the listener for each toggleable button
        toggleButton(sundayToggle, 0);
        toggleButton(mondayToggle, 1);
        toggleButton(tuesdayToggle, 2);
        toggleButton(wednesdayToggle, 3);
        toggleButton(thursdayToggle, 4);
        toggleButton(fridayToggle, 5);
        toggleButton(saturdayToggle, 6);


        //ToDo test to make sure the togglebutton method actually works

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

    // This looks ugly af but don't worry because it still deletes the habit
    public void deleteHabit(){
        this.user.getHabitList().deleteHabit(user.getHabitList().getHabit(this.position));
        saveInFile();
        finish();
    }

    public void createHabit(){
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        EditText dateCalendar = (EditText) findViewById(R.id.dateCalendar);

        // This will create the habit object using the controller
        Habit habit = habitController.createHabit(nameText.getText().toString(), commentText.getText().toString(), this.schedule);

        // If the habit constraints aren't met we could throw a toast notification here
        // We also won't finish the activity
        if (habit == null) {
            Toast.makeText(CreateHabitActivity.this, "Habit name is max 20 characters and comment max 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the valid habit to the user
        this.user.addHabit(habit);

        // Save the new user to the user save file
        saveInFile();

        // Finish the activity and take us back to the main habit screen
        finish();
    }

    public void editHabit() {
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        EditText dateCalendar = (EditText) findViewById(R.id.dateCalendar);
        HabitList habitList = user.getHabitList();
        Habit habit = habitList.getHabit(this.position);

        if (habitController.editHabit(user.getHabitList().getHabit(this.position), nameText.getText().toString(), commentText.getText().toString(), this.schedule) == -1){
            Toast.makeText(CreateHabitActivity.this, "Habit name is max 20 characters and comment max 30 characters", Toast.LENGTH_SHORT).show();
            return;
            }
        // do nothing if edit returns -1

        saveInFile();
        finish();
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
