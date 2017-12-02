package h4bit.h4bit.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
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
import h4bit.h4bit.Manifest;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * DoHabitActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class DoHabitActivity extends AppCompatActivity {

    private Habit theHabit;
    private User user;
    private HabitEventList habitEventList;
    private EditText commentText;
    private SaveLoadController saveLoadController;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location eventLocation;
    private Context context;
    private static final int locationPermission = 1;
    private Activity activity;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback = new LocationCallback();

    @Override
    protected void onStart(){
        String savefile;

        super.onStart();
        setContentView(R.layout.do_habit_activity);

        // init context and activity
        this.context = getApplicationContext();
        this.activity = this;

        // Init the saveload controller
        savefile = getIntent().getStringExtra("savefile");
        this.saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // location is null as default
        eventLocation = null;


        // init the fusedlocationthing
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        createLocationRequest();

        // Get the habit that was clicked on as well as the user habitEventList
        final int position = getIntent().getIntExtra("position", -1);
        this.theHabit = user.getHabitList().getHabit(position);
        this.habitEventList = user.getHabitEventList();

        // Initialize the buttons and UI objects
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button doHabitButton = (Button) findViewById(R.id.doHabitButton);
        Button uploadImageButton = (Button) findViewById(R.id.uploadHabitPictureButton);
        final ToggleButton locationToggle = (ToggleButton) findViewById(R.id.locationToggle);
        commentText = (EditText) findViewById(R.id.addCommentText);

        // Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                onBackPressed();
            }
        });

        // Confirm doHabit button
        doHabitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                user.getHabitList().sortByNextDate();
                theHabit = user.getHabitList().getHabit(position);
                if(commentText.getText().toString().equals("")){
                    theHabit.doHabit(eventLocation, habitEventList);
                } else {
                    theHabit.doHabit(commentText.getText().toString(), eventLocation, habitEventList);
                }

                saveLoadController.save(user);
                finish();
            }
        });

        // Location toggle button
        locationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // This will turn location on our off, rather, clicking the button will
                // activate location and store it in habitEvent
                // and toggling it off will make that location NULL
                if (isChecked) {
                    getCurrentLocation(locationToggle);

                }else{
                    eventLocation = null;
                    //Toast.makeText(context, "PERMISSION DENIED", Toast.LENGTH_LONG).show();

                }
            }
        });
        //https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        uploadImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);

            }
        });


    }

    // This is taken from the google play location guide
    // It sets up the location updates
    //https://developer.android.com/training/location/receive-location-updates.html
    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // Idk what this does @ alex
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
                // TODO Auto-generated catch block2
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void getCurrentLocation(ToggleButton locationToggle){
        // this taken directly from the android tutorial
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted we can show a rationale
            // lets not
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, locationPermission);
            locationToggle.setChecked(false);


            // if statement covers if they have already given permission
            // If they did not we will not get the location
            // We will also leave the button untoggled
            // We could possibly hide the button, or disallow any interaction with it
            // unless location services is allowed

        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location!
                    if (location != null) {
                        eventLocation = location;
                        Toast.makeText(context, "Location received", Toast.LENGTH_LONG).show();


                    } else {
                        // Maybe a toast saying cannot get location
                        Toast.makeText(context, "Could not get location", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case locationPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // Get the location here I guess
//                    getCurrentLocation();

                } else {
                    eventLocation = null;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
