package h4bit.h4bit.Views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import h4bit.h4bit.Controllers.HabitController;
import h4bit.h4bit.Controllers.HabitEventController;
import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.R;

/**
 * Created by Vlad Kravchnko on 11/13/2017.
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
            theHabitEvent = habitEventList.getHabitEvent(position);
            Log.d("test0", theHabitEvent.getHabit().getName());
            TextView habitEventName = (TextView) view.findViewById(R.id.HabitEventName);
            TextView habitEventDate = (TextView) view.findViewById(R.id.HabitEventDate);
            //TextView completed = (TextView) view.findViewById(R.id.completed);
            //TextView missed = (TextView) view.findViewById(R.id.missed);
            //TextView nextDate = (TextView) view.findViewById(R.id.nextDate);
            //ImageButton editButton = (ImageButton) view.findViewById(R.id.editButton);

            //editButton.setOnClickListener(new View.OnClickListener(){
                //public void onClick(View view){
                    // This will take us to the edit habit activity
                    //Intent intent = new Intent(context, CreateHabitActivity.class);
                    //intent.putExtra("savefile", savefile);
                    //intent.putExtra("mode", "edit");
                  //  intent.putExtra("position", position);
                //    context.startActivity(intent);
              //  }
            //});

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    theHabitEvent = habitEventList.getHabitEvent(position);
                    Intent intent = new Intent(view.getContext(), EditHabitActivity.class);
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
