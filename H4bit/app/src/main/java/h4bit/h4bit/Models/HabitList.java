package h4bit.h4bit.Models;

import java.util.ArrayList;
import java.util.Collections;

/** User class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitList {
    private ArrayList<Habit> habitArrayList;

    public HabitList(){
        habitArrayList = new ArrayList<Habit>();
    }

    /**
     *
     * @param theHabit
     */
    public void addHabit(Habit theHabit){
        habitArrayList.add(theHabit);
    }

    /**
     *
     * @param theHabit
     * @return
     */
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

    public int getSize(){
        return habitArrayList.size();
    }

    public Habit getHabit(int i){
        return habitArrayList.get(i);
    }

    /**
     * sorts habits by when they are next scheduled to be done
     */
    public void sortByNextDate(){
        for(int i = 0; i < habitArrayList.size(); i++){
            habitArrayList.get(i).setNextDate();
        }
        Collections.sort(habitArrayList);
    }

}
