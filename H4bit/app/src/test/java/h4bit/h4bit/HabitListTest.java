package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Arrays;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.Views.MainHabitActivity;

/**
 * Created by Vlad Kravchnko on 10/21/2017.
 */

public class HabitListTest extends ActivityInstrumentationTestCase2 {
    public HabitListTest(){
        super (MainHabitActivity.class);
    }

    public void testAddHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        habits.addHabit(habit1);
        assertTrue(habits.hasHabit(habit1));
    }

    public void testHasHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        assertFalse(habits.hasHabit(habit1));
        habits.addHabit(habit1);
        assertTrue(habits.hasHabit(habit1));
    }
    public void testDeleteHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        habits.addHabit(habit1);
        habits.deleteHabit(habit1);
        assertFalse(habits.hasHabit(habit1));
    }
    public void testGetHabit(){
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        habits.addHabit(habit1);
        Habit ReturnedHabit = habits.getHabit(0);
        assertEquals(habit1, ReturnedHabit);







    }



}
