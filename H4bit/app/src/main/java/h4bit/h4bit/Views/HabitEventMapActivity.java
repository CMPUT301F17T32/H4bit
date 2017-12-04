package h4bit.h4bit.Views;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import h4bit.h4bit.Controllers.LocationController;
import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/** HabitEventMapActivity
 * version 1.0
 * 2017-11-09.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 *
 * This should display the map along with all the users tracked location habit events
 */

public class HabitEventMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private HabitEventList habitEventList;
    private HabitEventList socialEventList;
    private String mode;
    private Location currentLocation;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init local variables
        String savefile;
        FragmentManager fragmentManager;
        SaveLoadController saveLoadController;
        User user;
        MapFragment mapFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_map);

        // Load the mode
        mode = getIntent().getStringExtra("mode");

        // Load save
        savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // Get habitEventList
        habitEventList = user.getHabitEventList();

        // Get habits from social screen
        if (!mode.equals("history")) {
            // Get the social habitEventList from intent
            socialEventList = new Gson().fromJson(getIntent().getStringExtra("socialEventList"), HabitEventList.class);
            // We also need the current location for nearby
            if (mode.equals("nearby")){
                // Call twice just in case they activate location the first time
                currentLocation = new LocationController(activity).getCurrentLocation();
                currentLocation = new LocationController(activity).getCurrentLocation();
            }
        }

        // Init the map
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    // This initializes the map and is called implicitly im pretty sure
    @Override
    public void onMapReady(GoogleMap map) {
        // We need to use this addMarker to add all the habits with locations
        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        // First we need to get all the habitEvents
        // Then we need to iterate through them and add a marker for each event with a location
        if (mode.equals("history")) {
            for (int i = 0; i < habitEventList.size(); i++) {
                HabitEvent habitEvent = habitEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                }
            }
        } else if (mode.equals("nearby") && currentLocation!=null){
            for (int i = 0; i < habitEventList.size(); i++) {
                HabitEvent habitEvent = habitEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    // We need to make sure if this float comparison works
                    if(currentLocation.distanceTo(location) <= 5.0) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                    }
                }
            }
            // Now loop through the social events
            // Note that this will just mark with the event title, and not whodunit
            for (int i = 0; i < socialEventList.size(); i++) {
                HabitEvent habitEvent = socialEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    // We need to make sure if this float comparison works
                    if(currentLocation.distanceTo(location) <= 5.0) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                    }
                }
            }
            // Display follow and your own social markers within 5km's of current location
        } else {
            for (int i = 0; i < habitEventList.size(); i++) {
                HabitEvent habitEvent = habitEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                }
            }
            // Display all friends markers
        }
    }
    // This is what actually does the request for the Location feature
    // It checks the users permissions, requests permissions
    // Sends toast feedback to the user

}
