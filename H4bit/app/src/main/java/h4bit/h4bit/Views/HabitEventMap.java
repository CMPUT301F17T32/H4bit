package h4bit.h4bit.Views;

import android.app.FragmentManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * This will display the map the user can interact with
 */

public class HabitEventMap extends AppCompatActivity implements OnMapReadyCallback{

    private String savefile;
    private FragmentManager fragmentManager;
    private SaveLoadController saveLoadController;
    private User user;
    private MapFragment mapFragment;
    private HabitEventList habitEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_map);

        // Get intent

        // Load save
        this.savefile = getIntent().getStringExtra("savefile");
        saveLoadController = new SaveLoadController(savefile, this.getApplicationContext());
        user = saveLoadController.load();
        // Get habitEventList
        habitEventList = user.getHabitEventList();

        // Init the map
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // We need to use this addMarker to add all the habits with locations
        //map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        // First we need to get all the habitEvents
        // Then we need to iterate through them and add a marker for each event with a location
        for (int i = 0; i < habitEventList.size(); i++) {
            HabitEvent habitEvent = habitEventList.get(i);
            if (habitEvent.getLocation() != null){
                Location location = habitEvent.getLocation();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title(habitEvent.getHabit().getName()));
            }
        }
    }

    // This app is to prevent the default back button crashing the app
    // I don't know why it crashes with the default behaviour so this feels like a bandaid fix
    // Taken from https://stackoverflow.com/questions/32296923/crashing-when-back-to-parent-activity-jsonexception-no-value-for-response
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
