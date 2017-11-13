package h4bit.h4bit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.R;

/**
 * ViewFollowListActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

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
