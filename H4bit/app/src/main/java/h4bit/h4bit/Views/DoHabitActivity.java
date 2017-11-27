package h4bit.h4bit.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * DoHabitActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class DoHabitActivity extends AppCompatActivity {

    private Habit theHabit;
    private String savefile;
    private User user;
    private HabitEventList habitEventList;
    private EditText commentText;
    private SaveLoadController saveLoadController;

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.do_habit_activity);

        // Init the saveload controller
        this.savefile = getIntent().getStringExtra("savefile");
        this.saveLoadController = new SaveLoadController(this.savefile, this.getApplicationContext());
        user = saveLoadController.load();
//        loadFromFile();

        final int position = getIntent().getIntExtra("position", -1);
        this.theHabit = user.getHabitList().getHabit(position);


        this.habitEventList = user.getHabitEventList();

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button doHabitButton = (Button) findViewById(R.id.doHabitButton);
        Button uploadImageButton = (Button) findViewById(R.id.uploadHabitPictureButton);
        ToggleButton locationToggle = (ToggleButton) findViewById(R.id.locationToggle);
        commentText = (EditText) findViewById(R.id.addCommentText);

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                onBackPressed();
            }
        });

        doHabitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                user.getHabitList().sortByNextDate();
                theHabit = user.getHabitList().getHabit(position);
                if(commentText.getText().toString().equals("")){
                    theHabit.doHabit(habitEventList);
                } else {
                    theHabit.doHabit(commentText.getText().toString(), habitEventList);
                }
                saveLoadController.save(user);
//                saveInFile();
                finish();
            }
        });

        //https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        uploadImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
