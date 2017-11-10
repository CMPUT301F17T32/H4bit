package h4bit.h4bit.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.R;

/**
 * Created by Alex on 2017-11-09.
 */

public class HabitAdapter extends BaseAdapter {

    private ArrayList<Habit> habitArrayList;
    private Context context;

    public HabitAdapter(Context context, ArrayList<Habit> habits) {
        this.habitArrayList = habits;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_habit, null);
        }

        //Initialize view components
        Habit theHabit = habitArrayList.get(position);
        TextView habitName = (TextView) view.findViewById(R.id.habitName);
        TextView completionRate = (TextView) view.findViewById(R.id.completionRate);

        if(theHabit.getCompletionRate() == -1){
            completionRate.setText("N/A");
        } else {
            completionRate.setText(String.valueOf(Math.round(theHabit.getCompletionRate())));
        }

        habitName.setText(theHabit.getName());

        return view;
    }

    @Override
    public int getCount(){
        return habitArrayList.size();
    }

    @Override
    public Object getItem(int pos) {
        return habitArrayList.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return pos;
    }

}
