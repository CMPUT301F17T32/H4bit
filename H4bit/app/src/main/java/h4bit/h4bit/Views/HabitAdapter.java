package h4bit.h4bit.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        TextView completed = (TextView) view.findViewById(R.id.completed);
        TextView missed = (TextView) view.findViewById(R.id.missed);
        TextView nextDate = (TextView) view.findViewById(R.id.nextDate);

        habitName.setText(theHabit.getName());

        if(theHabit.getCompletionRate() == -1){
            completionRate.setText("N/A");
        } else {
            completionRate.setText(String.valueOf(Math.round(theHabit.getCompletionRate())));
        }

        completed.setText(String.valueOf(theHabit.getCompleted()));
        missed.setText(String.valueOf(theHabit.getMissed()));

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;


        int nextDateInt = theHabit.getNextDate(dayNumber);
        if(nextDateInt == 0){
            nextDate.setText(R.string.today);
        } else if (nextDateInt == 1){
            nextDate.setText(R.string.tomorrow);
        } else {
            nextDateInt = ((dayNumber + nextDateInt) % 7) + 1;
            calendar.add(Calendar.DATE, nextDateInt);
            nextDate.setText(new SimpleDateFormat("EEEE").format(calendar.getTime()));
        }


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
