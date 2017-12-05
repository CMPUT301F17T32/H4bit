package h4bit.h4bit.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import h4bit.h4bit.Models.Follow;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * ProfileController
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class StatusAdapter extends BaseAdapter{

    private ArrayList<HabitEvent> theList;
    private Context context;
    private String savefile;
    private User theUser;

    public StatusAdapter(Context context, ArrayList<HabitEvent> theList, String savefile) {
        this.theList = theList;
        this.context = context;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_follow, null);
        }
        TextView statusUsernameText = (TextView) view.findViewById(R.id.statusUsernameText);
        TextView habitStatusDateText = (TextView) view.findViewById(R.id.habitStatusDateText);
        TextView habitStatusCommentText = (TextView) view.findViewById(R.id.habitStatusCommentText);
        TextView habitStatusNameText = (TextView) view.findViewById(R.id.habitStatusNameText);
        TextView habitStatusPercent = (TextView) view.findViewById(R.id.habitStatusPercent);
        ImageView habitStatusEventImage = (ImageView) view.findViewById(R.id.habitStatusEventImage);

        statusUsernameText.setText(theList.get(position).getHabit().getUsername());
        habitStatusDateText.setText(theList.get(position).getDate().toString());
        habitStatusCommentText.setText(theList.get(position).getComment());
        habitStatusNameText.setText(theList.get(position).getHabit().getName());
        habitStatusPercent.setText(String.valueOf(theList.get(position).getHabit().getCompletionRate()) + "%");
        habitStatusEventImage.setImageBitmap(theList.get(position).getImage());

    return view;
    }

    /**
     *
     * @return habitlist size
     */
    @Override
    public int getCount(){
        return theList.size();
    }

    /**
     *
     * @param pos for indexing
     * @return habit
     */
    @Override
    public Object getItem(int pos) {
        return theList.get(pos);
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
