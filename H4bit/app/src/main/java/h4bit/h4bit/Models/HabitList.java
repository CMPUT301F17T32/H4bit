package h4bit.h4bit.Models;

import java.util.ArrayList;
import java.util.Collections;

/** HabitList class
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
     * Add a habit to the list
     * @param theHabit habit object to be added to list
     */
    public void addHabit(Habit theHabit){
        habitArrayList.add(theHabit);
    }

    /**
     * Checks if the habit list has a habit
     * @param theHabit habit object to be searched for in list
     * @return boolean true if the list has the habit, false otherwise
     */
    public boolean hasHabit(Habit theHabit){
        if(habitArrayList.contains(theHabit)){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<HabitEvent> getMostRecentForEachHabit(){
        //some1
        ArrayList<HabitEvent> theList = new ArrayList<>();

        return theList;
    }

    /**
     * Deletes a habit from the list
     * @param theHabit
     */
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
     * Sorts habits by when they are next scheduled to be done
     */
    public void sortByNextDate(){
        for(int i = 0; i < habitArrayList.size(); i++){
            habitArrayList.get(i).setNextDate();
        }
        Collections.sort(habitArrayList);
    }

}
