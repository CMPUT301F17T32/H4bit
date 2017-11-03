package h4bit.h4bit;

import java.util.ArrayList;

/**
 * Created by bhlewka on 11/3/17.
 */

public class HabitEventList extends HabitList{
    private ArrayList<HabitEvent> habitEventArrayList;

    public void addHabitEvent(HabitEvent habitEvent){
        this.habitEventArrayList.add(habitEvent);
    }
}
