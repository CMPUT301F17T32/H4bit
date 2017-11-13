package h4bit.h4bit.Controllers;

import h4bit.h4bit.Models.Habit;

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
        if(comment.length() > 30 || name.length() > 20)
            return habit;

        return new Habit(name, comment, schedule);
    }

    public int editHabit(Habit habit,  String name, String comment, boolean[] schedule){
        //does this perform the edits by value?
        //todo handle constraints
        // name no more than 20
        // comment no more than 30

        //if constraints not met
        if(comment.length() > 30 || name.length() > 20)
            return -1;

        habit.setComment(comment);
        habit.setName(name);
        habit.setSchedule(schedule);
        return 1;

//        return habit;
    }

    public void doHabit(){

    }

}
