package h4bit.h4bit.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/** User class
 * version 1.0
 * 2017-11-03.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitEventList {
    private ArrayList<HabitEvent> habitEventList;

    public HabitEventList(){
        habitEventList = new ArrayList<HabitEvent>();
    }

    public void addHabitEvent(HabitEvent habitEvent){
        this.habitEventList.add(habitEvent);
    }

    public void sortByDate(){
        Collections.sort(habitEventList);
    }

    public ArrayList<HabitEvent> getMostRecentForEachHabit(){

        // This is the size of the habitEventList
        int size = this.size();
        ArrayList<HabitEvent> theList = new ArrayList<>();

        // We will iterate over each habitEvent in the habitEventList
        for(int i = 0; i < size; i++){
            HabitEvent habitEvent = this.get(i);
            // Iterate through theList to see if we already have a habit of that type
            // if we do have a habit of that type, keep the one with the more recent date
            int j = 0;
            for(j = 0; j < theList.size(); j++){
                HabitEvent habitEvent1 = theList.get(j);
                // if the names are equal, means the habitEvents are "equal"
                if(habitEvent.getHabit().getName().equals(habitEvent1.getHabit().getName())){
                    // if the new date is greater than the old date
                    // remove the old object and add the new one to thelist
                    if(habitEvent.getDate().getTime() > habitEvent1.getDate().getTime()){
                        theList.remove(habitEvent1);
                        theList.add(habitEvent);
                        break;
                    }
                    // Break and move on to the next habit if it is not more recent
                    break;
                }
            }
            // means we iterated through the list without doing a swap, and without
            // finding a match, so add habit to theList
            if(j == theList.size()){
                theList.add(habitEvent);
            }
        }
        // The list should now contain one of each habit, each being the most recent of that habit
        return theList;
    }

    /**
     * determines whether a habit has been completed today based on events
     * @param habit the type
     * @return true or false
     */
    public boolean isDoneToday(Habit habit){
        Date date = new Date();
        this.sortByDate();
        for(int i = 0; i < this.size(); i++){
            if(Objects.equals(this.get(i).getHabit().getName(), habit.getName())){
               if(this.get(i).getDate().getYear() == date.getYear() &&
                        this.get(i).getDate().getMonth() == date.getMonth() &&
                        this.get(i).getDate().getDate() == date.getDate()){
                    return true;
                }
            }
        }
        return false;
    }

    public HabitEvent get(int pos){
        return habitEventList.get(pos);
    }

    public int size(){
        return habitEventList.size();
    }

    public void deleteHabitEvent(HabitEvent habitEvent){
        this.habitEventList.remove(habitEvent);
    }

    public void clearList(){  habitEventList.clear();}
}
