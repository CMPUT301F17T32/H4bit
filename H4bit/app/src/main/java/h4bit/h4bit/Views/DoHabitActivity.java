package h4bit.h4bit.Views;

import android.support.v7.app.AppCompatActivity;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.R;

/**
 * Created by benhl on 2017-10-29.
 */

public class DoHabitActivity extends AppCompatActivity {

    private Habit theHabit;

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.do_habit_activity);

    }

}
