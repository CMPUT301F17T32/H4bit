package h4bit.h4bit.Controllers;

import java.util.Date;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.User;

/** HabitController
 * version 1.0
 * 2017-11-09.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 *
 * This controller should handle all data regarding habits and habitLists
 */

public class HabitController {

    public HabitController(){

    }

    /*
    This should initialize a habit adhering to constraints and return it
     */

    /**
     *
     * @param name
     * @param comment
     * @param schedule
     * @return habit
     */
    public Habit createHabit(String name, String comment, boolean[] schedule){
        // One constraint should be to not create a habit with an all false boolean array

        // Return null habit if constraints not met
        Habit habit = null;
        if(comment.length() > 30 || name.length() > 20)
            return habit;

        return new Habit(name, comment, schedule);
    }

    /**
     *
     * @param habit
     * @param name
     * @param comment
     * @param schedule
     * @return
     */
    public int editHabit(User user, Habit habit, String name, String comment, boolean[] schedule, Date startDate){
        //does this perform the edits by value?
        // name no more than 20
        // comment no more than 30

        //if constraints not met
        if(comment.length() > 30 || name.length() > 20)
            return -1;

        habit.setComment(comment);
        if(!habit.getName().equals(name)){
            for(int i = 0; i < user.getHabitEventList().size(); i++){
                if(user.getHabitEventList().get(i).getHabit().getName().equals(habit.getName())){
                    user.getHabitEventList().get(i).getHabit().setName(name);
                }
            }
            habit.setName(name);

        }
        habit.setSchedule(schedule);

        habit.setStartDate(startDate, user.getHabitEventList());
        habit.updateStats(user.getHabitEventList());

        return 1;

//        return habit;
    }

    public void doHabit(){

    }

}
