package com.example.james.habitapp;

import java.util.ArrayList;

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

}
