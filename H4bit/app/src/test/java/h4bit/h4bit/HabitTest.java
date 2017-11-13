package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Arrays;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.Views.MainHabitActivity;

/**
 * Created by Vlad Kravchnko on 10/22/2017.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {
    public HabitTest() {
        super(MainHabitActivity.class);
    }

    public void testCreateHabit() {
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        String name = "name1";
        String comment = "comment1";
        Habit habit1 = new Habit("name1", "comment1", sched);
        assertEquals(habit1.getName(), name);
        assertEquals(habit1.getComment(),comment);
        assertEquals(habit1.getSchedule(), sched);
    }
     public void testEditSchedule(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);

         Habit habit1 = new Habit("name1", "comment1", sched);
         boolean[] sched1 = new boolean[7];
         sched1[0]=true;
         habit1.editSchedule(sched1);
         boolean [] schedTest= habit1.getSchedule();
         assertFalse(schedTest[0]);
     }
     public void testGetCompletionRate(){
         HabitEventList habitEventList = new HabitEventList();
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         Habit habit = new Habit("name", "comment", sched);
         habit.doHabit(habitEventList);
         assertEquals(habit.getCompletionRate(), 100);
     }
     public void testSetNextDate(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         Habit habit = new Habit("name", "comment", sched);
         int date = habit.getNextDate();
         habit.setNextDate();
         assertEquals(habit.getNextDate(), date+1);
     }
     public void testGetNextDayString(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         Habit habit = new Habit("name", "comment", sched);
         assertEquals(habit.getNextDayString(), "Today");
         habit.setNextDate();
         assertEquals(habit.getNextDayString(), "Tomorrow");
     }

}