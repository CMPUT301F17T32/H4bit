package h4bit.h4bit.Models;

import java.util.ArrayList;

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

    public void deleteHabitEvent(HabitEvent habitEvent){
        this.habitEventList.remove(habitEvent);
    }

}
