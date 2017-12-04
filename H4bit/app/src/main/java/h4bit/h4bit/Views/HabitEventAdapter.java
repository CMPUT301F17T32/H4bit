package h4bit.h4bit.Views;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

import h4bit.h4bit.Controllers.HabitController;
import h4bit.h4bit.Controllers.HabitEventController;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;

/**
 * HabitEventAdapter
 * Version 1.0
 * November 13th 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitEventAdapter extends BaseAdapter {

    private HabitEventList habitEventList;
    private Context context;
    private HabitEventController habitEventController;
    private String savefile;
    private HabitEvent theHabitEvent;

    public HabitEventAdapter(Context context, HabitEventList habitEventList, String savefile) {
        this.habitEventList = habitEventList;
        this.context = context;
        this.habitEventController = new HabitEventController();
        this.savefile = savefile;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_habit_event, null);
        }

        //Initialize view components
        theHabitEvent = habitEventList.get(position);
        TextView habitEventName = (TextView) view.findViewById(R.id.habitEventName);
        TextView habitEventDate = (TextView) view.findViewById(R.id.habitEventDate);
        TextView habitEventComment = (TextView) view.findViewById(R.id.habitEventComment);
        ImageButton editButton = (ImageButton) view.findViewById(R.id.editButton);
        ImageView eventImageView = (ImageView) view.findViewById(R.id.eventImage);

        habitEventName.setText(theHabitEvent.getHabit().getName());
        habitEventDate.setText(theHabitEvent.getDate().toString());
        habitEventComment.setText(theHabitEvent.getComment());
        Log.d("bitmap 2", String.valueOf(theHabitEvent.getImage()));

        eventImageView.setImageBitmap(theHabitEvent.getImage());

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                theHabitEvent = habitEventList.get(position);
                Intent intent = new Intent(view.getContext(), EditHabitEventActivity.class);
                intent.putExtra("savefile", savefile);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }

        });
        return view;
    }

    @Override
    public HabitEvent getItem(int pos){
        return this.theHabitEvent;
    }

    public long getItemId(int pos) {return pos;}

    public int getCount(){
        return habitEventList.size();
    }

}