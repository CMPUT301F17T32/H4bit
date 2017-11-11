package h4bit.h4bit.Models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alex on 2017-10-24.
 */

public class HabitList {
    private ArrayList<Habit> habitArrayList;

    public HabitList(){
        habitArrayList = new ArrayList<Habit>();
    }

    public void addHabit(Habit theHabit){
        habitArrayList.add(theHabit);
    }

    public boolean hasHabit(Habit theHabit){
        if(habitArrayList.contains(theHabit)){
            return true;
        } else {
            return false;
        }
    }

    public void deleteHabit(Habit theHabit){
        habitArrayList.remove(theHabit);
    }

    public Habit getHabit(int i){
        return habitArrayList.get(i);
    }

    // This is a bad function that should probably be deleted
    // I dont know how to make adapters so Im making this just for prototype convenience
    public ArrayList<Habit> getRawList(){
        return habitArrayList;
    }

    public void sortByNextDate(){
        for(int i = 0; i < habitArrayList.size(); i++){
            habitArrayList.get(i).setNextDate();
        }
        Collections.sort(habitArrayList);
    }

}
