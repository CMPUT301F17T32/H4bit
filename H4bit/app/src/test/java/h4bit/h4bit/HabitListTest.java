package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Arrays;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.Views.MainHabitActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Vlad Kravchnko on 10/21/2017.
 */

public class HabitListTest{

    @Test
    public void testAddHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        habits.addHabit(habit1);
        assertTrue(habits.hasHabit(habit1));
    }
    @Test
    public void testHasHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        assertFalse(habits.hasHabit(habit1));
        habits.addHabit(habit1);
        assertTrue(habits.hasHabit(habit1));
    }
    @Test
    public void testDeleteHabit() {
        HabitList habits = new HabitList();
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Habit habit1 = new Habit("name1", "comment1", sched);
        habits.addHabit(habit1);
        habits.deleteHabit(habit1);
        assertFalse(habits.hasHabit(habit1));
    }
    @Test
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
