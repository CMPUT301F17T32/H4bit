package h4bit.h4bit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import h4bit.h4bit.Controllers.SaveLoadController;
import h4bit.h4bit.R;

/**
 * ViewProfileActivity
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class ViewProfileActivity extends AppCompatActivity {

    private String savefile;
    private SaveLoadController saveLoadController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_habit);
    }
}
