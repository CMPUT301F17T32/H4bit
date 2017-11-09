package h4bit.h4bit;

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

}
