package h4bit.h4bit.Controllers;

import android.content.Intent;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Views.CreateHabitActivity;

/**
 * Created by benhl on 2017-11-09.
 *
 * This controller should handle all data regarding habits and habitLists
 */

public class HabitController {

    public HabitController(){

    }

    // This should initialize a habit adhering to constraints and return it
    public Habit createHabit(String name, String comment, boolean[] schedule){
        // Todo perform constraint checking, maybe return null habit if constraints not met
        // One constraint should be to not create a habit with an all false boolean array

        // Return null habit if constraints not met
        Habit habit = null;
        return new Habit(name, comment, schedule);
    }

    // This should take a habit as an argument, as well as all the values that should be updated
    public Habit editHabit(Habit habit, String name, String comment, boolean[] schedule) {
        //todo set constraints
        // does this perform the edits by value?
        habit.setComment(comment);
        habit.setName(name);
        habit.setSchedule(schedule);

        return habit;
    }

    public void doHabit(){

    }

}
