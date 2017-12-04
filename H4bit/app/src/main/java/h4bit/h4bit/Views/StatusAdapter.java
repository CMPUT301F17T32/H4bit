package h4bit.h4bit.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import h4bit.h4bit.Models.Follow;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * ProfileController
 * Version 1.0
 * November 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class StatusAdapter extends BaseAdapter{

    private ArrayList<String> followList;
    private Context context;
    private String savefile;
    private User theUser;

    public StatusAdapter(Context context, ArrayList<String> followList, String savefile) {
        this.followList = followList;
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

    return view;
    }

    /**
     *
     * @return habitlist size
     */
    @Override
    public int getCount(){
        return followList.size();
    }

    /**
     *
     * @param pos for indexing
     * @return habit
     */
    @Override
    public Object getItem(int pos) {
        return followList.get(pos);
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
