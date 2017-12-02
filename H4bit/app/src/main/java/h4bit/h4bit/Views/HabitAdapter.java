package h4bit.h4bit.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import h4bit.h4bit.Controllers.HabitController;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;

/**
 * HabitAdapter
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitAdapter extends BaseAdapter {

    private HabitList habitList;
    private Context context;
    private HabitController habitController;
    private String savefile;
    private Habit theHabit;

    public HabitAdapter(Context context, HabitList habitList, String savefile) {
        this.habitList = habitList;
        this.context = context;
        this.habitController = new HabitController();
        this.savefile = savefile;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_habit, null);
        }

        //Initialize view components
        theHabit = habitList.getHabit(position);
        TextView habitName = (TextView) view.findViewById(R.id.habitName);
        TextView completionRate = (TextView) view.findViewById(R.id.completionRate);
        TextView completed = (TextView) view.findViewById(R.id.completed);
        TextView missed = (TextView) view.findViewById(R.id.missed);
        TextView nextDate = (TextView) view.findViewById(R.id.nextDate);
        ImageButton editButton = (ImageButton) view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               // This will take us to the edit habit activity
               Intent intent = new Intent(context, CreateHabitActivity.class);
               intent.putExtra("savefile", savefile);
               intent.putExtra("mode", "edit");
               intent.putExtra("position", position);
               context.startActivity(intent);
           }
        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                theHabit = habitList.getHabit(position);
                Date theDate = new Date();
                Log.d("lel", String.valueOf(theDate.getYear()));
                if(theHabit.getNextDate() == 0 && !theHabit.getDoneToday() &&
                        theHabit.getStartDate().getYear() <= (theDate.getYear() + 1900) &&
                        theHabit.getStartDate().getMonth() <= theDate.getMonth() &&
                        theHabit.getStartDate().getDate() <= theDate.getDate()){
                    Intent intent = new Intent(view.getContext(), DoHabitActivity.class);
                    intent.putExtra("savefile", savefile);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            }

        });

        habitName.setText(theHabit.getName());

        if(theHabit.getCompletionRate() == -1){
            completionRate.setText("N/A");
        } else {
            completionRate.setText(String.valueOf(Math.round(theHabit.getCompletionRate())) + "%");
        }

        completed.setText(String.valueOf(theHabit.getCompleted()));
        missed.setText(String.valueOf(theHabit.getMissed()));
        nextDate.setText(theHabit.getNextDayString());

        return view;
    }

    /**
     *
     * @return habitlist size
     */
    @Override
    public int getCount(){
        return habitList.getSize();
    }

    /**
     *
     * @param pos for indexing
     * @return habit
     */
    @Override
    public Object getItem(int pos) {
        return habitList.getHabit(pos);
    }

    /**
     *
     * @param pos for indexing
     * @return itemID
     */
    @Override
    public long getItemId(int pos){
        return pos;
    }

}
