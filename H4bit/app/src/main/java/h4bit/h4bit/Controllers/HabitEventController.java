package h4bit.h4bit.Controllers;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;

/**
 * This should handle all data regarding habitEvents and habitEventLists
 */

public class HabitEventController {

    public HabitEventController(){

    }
    public void editHabitEvent(String habitName, String comment){

        habitEvent.setComment(comment);
        habitEvent.habit.setName(habitName);
    }
}
