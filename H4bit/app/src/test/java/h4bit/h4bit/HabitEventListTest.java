package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Views.MainHabitActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by benhl on 2017-11-13.
 */

public class HabitEventListTest{

    @Test
    public void testAddHabitEvent(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEvent, habitEventList.get(0));
    }

    @Test
    public void testIsDoneToday(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        HabitEvent habitEvent = new HabitEvent(habit,"comment");
        habitEventList.addHabitEvent(habitEvent);
        assertFalse(habitEventList.isDoneToday(habit));
    }
    @Test
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
    @Test
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
