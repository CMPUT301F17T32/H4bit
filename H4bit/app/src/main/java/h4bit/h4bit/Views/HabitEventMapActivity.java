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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
    private Activity activity;
    private Context context;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int locationPermission = 1;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback = new LocationCallback();
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init local variables
        Log.d("HabitEventMapActivity", "onCreate ran successfully");
        String savefile;
        FragmentManager fragmentManager;
        SaveLoadController saveLoadController;
        User user;
        MapFragment mapFragment;

        this.activity = this;
        this.context = this.getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_map);

        // Load the mode
        mode = getIntent().getStringExtra("mode");
        Log.d("HabitEventMapActivity", mode + " mode");

        // Load save
        savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();

        // Get habitEventList
        habitEventList = user.getHabitEventList();

        // Init the location thingy
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        createLocationRequest();

        // Get habits from social screen
        if (!mode.equals("history")) {
            // Get the social habitEventList from intent
            socialEventList = new Gson().fromJson(getIntent().getStringExtra("socialEventList"), HabitEventList.class);
            Log.d("HabitEventMapActivity", "Got the social event list? "+ socialEventList.get(0).getComment());
            // We also need the current location for nearby
            if (mode.equals("nearby")){
                // Call twice just in case they activate location the first time
                getCurrentLocation();
                getCurrentLocation();
            }
        }

        // Init the map
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    // This is what actually does the request for the Location feature
    // It checks the users permissions, requests permissions
    // Sends toast feedback to the user
    public boolean getCurrentLocation() {
        // this taken directly from the android tutorial
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted we can show a rationale
            // lets not
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, locationPermission);

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
                        currentLocation = location;
                        Toast.makeText(context, "Location received", Toast.LENGTH_LONG).show();
                        Log.d("HabitEventMapActivity", "Current Location: " + currentLocation.toString());
                        onMapReady(googleMap);


                    } else {
                        // Maybe a toast saying cannot get location
                        Toast.makeText(context, "Could not get location", Toast.LENGTH_LONG).show();
                        Log.d("HabitEventMapActivity", "Could not get location for some reason?");
                    }
                }
            });
        }
        return true;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case locationPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // Get the location here I guess

                } else {
                    currentLocation = null;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // This initializes the map and is called implicitly im pretty sure
    @Override
    public void onMapReady(GoogleMap map) {
        // We need to use this addMarker to add all the habits with locations
        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        // First we need to get all the habitEvents
        // Then we need to iterate through them and add a marker for each event with a location
        // This is an ugly method ugh
        // Should get current location be done in here instead????
        this.googleMap = map;

        // HISTORY MODE
        if (mode.equals("history")) {
            Log.d("HabitEventMapActivity", "History map async");

            for (int i = 0; i < habitEventList.size(); i++) {
                HabitEvent habitEvent = habitEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                }
            }
        // NEARBY MODE
        } else if (mode.equals("nearby") && currentLocation!= null){
            Log.d("HabitEventMapActivity", "Nearby map async");

            // Place the current location marker too!
            LatLng latLngCurrent = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.addMarker(new MarkerOptions().position(latLngCurrent).title("Current Location"));

            for (int i = 0; i < habitEventList.size(); i++) {
                HabitEvent habitEvent = habitEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    // We need to make sure if this float comparison works
                    String floater = String.valueOf(currentLocation.distanceTo(location));
                    Log.d("HabitEventMapActivity", "Habit Location: " + location.toString());
                    Log.d("HabitEventMapActivity", "Distance from current: "+floater);
                    if(currentLocation.distanceTo(location) <= 5000.0) {
                        Log.d("HabitEventMapActivity", "Placing marker");
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                    }
                }
            }
            // Now loop through the social events
            // Note that this will just mark with the event title, and not whodunit
            if(socialEventList.size() == 0)
                return;
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
        // SOCIAL MODE
        } else if (mode.equals("social")){
            Log.d("HabitEventMapActivity", "Social map async");

            if (socialEventList.size() == 0)
                return;
            for (int i = 0; i < socialEventList.size(); i++) {
                HabitEvent habitEvent = socialEventList.get(i);
                if (habitEvent.getLocation() != null) {
                    Location location = habitEvent.getLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
                }
            }
        }
    }
}
