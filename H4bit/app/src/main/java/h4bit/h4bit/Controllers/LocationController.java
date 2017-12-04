package h4bit.h4bit.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by benhl on 2017-12-04.
 */

public class LocationController {
    private Activity activity;
    private Context context;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int locationPermission = 1;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Location currentLocation;


    public LocationController(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        createLocationRequest();
        mLocationCallback = new LocationCallback();

    }

    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public Location getCurrentLocation(){
        // this taken directly from the android tutorial
        this.currentLocation = null;
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted we can show a rationale
            // lets not
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, locationPermission);
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location!
                    if (location != null) {
                        currentLocation = location;
                        Toast.makeText(context, "Location received", Toast.LENGTH_LONG).show();


                    } else {
                        // Maybe a toast saying cannot get location
                        Toast.makeText(context, "Could not get location", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return currentLocation;
    }

}
