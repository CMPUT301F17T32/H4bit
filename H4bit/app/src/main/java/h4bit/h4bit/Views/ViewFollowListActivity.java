package h4bit.h4bit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.R;

public class ViewFollowListActivity extends AppCompatActivity {

    private ArrayList<Habit> habitsArrayList;
    private ArrayAdapter<Habit> adapter;
    private ListView habitList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_follow_list);
        habitList = (ListView) findViewById(R.id.followerList);

    }
}
