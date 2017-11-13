package h4bit.h4bit.Models;

import java.util.ArrayList;

/**
 * Created by Alex on 2017-11-03.
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
    public HabitEvent getHabitEvent(int i){
        return habitEventList.get(i);
    }
    public int size(){
        return habitEventList.size();
    }
    public String[] toArray(String[] String){
        return habitEventList.toArray(String);
    }

}
