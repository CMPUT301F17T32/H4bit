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
     * Adds a habit to a habitList
     * @param theHabit the habit to be added
     */
    public void addHabit(Habit theHabit){
        habitArrayList.add(theHabit);
    }

    /**
     * checks if the list has a habit
     * @param theHabit the habit to check if it exists
     * @return returns true or flase
     */
    public boolean hasHabit(Habit theHabit){
        return habitArrayList.contains(theHabit);
    }

    /**
     * Deletes the habit
     * @param theHabit habit to be deleted
     */
    public void deleteHabit(Habit theHabit){
        habitArrayList.remove(theHabit);
    }

    /**
     * Gets the size of the habit List
     * @return size of habit list int
     */
    public int getSize(){
        return habitArrayList.size();
    }

    /**
     * Returns a habit
     * @param i index position of habit to be gotten
     * @return Returns habit at position i
     */
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
