package h4bit.h4bit.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

    public boolean isDoneToday(Habit habit){
        Date date = new Date();
        this.sortByDate();
        for(int i = 0; i < this.size(); i++){
            if(this.get(i).getHabit().getName().equals(habit.getName())){
                if(this.get(i).getDate().getDate() == date.getDate()){
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
