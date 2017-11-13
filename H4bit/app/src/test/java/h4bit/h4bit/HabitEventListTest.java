package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Views.MainHabitActivity;

/**
 * Created by benhl on 2017-11-13.
 */

public class HabitEventListTest extends ActivityInstrumentationTestCase2 {
    public HabitEventListTest() {
        super(MainHabitActivity.class);
    }

    public void testAddHabitEvent(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEvent, habitEventList.get(0));
    }

    public void testIsDoneToday(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        assertFalse(habitEventList.isDoneToday(habit));
    }
    public void testSize(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEventList.size(), 1);
    }
    // Collections is built in so we dont need to test that its sorted
    // We can safely assume that it is
    public void testDeleteHabitEvent(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        habitEventList.deleteHabitEvent(habitEvent);
        assertEquals(habitEventList.size(), 0);
    }
}
