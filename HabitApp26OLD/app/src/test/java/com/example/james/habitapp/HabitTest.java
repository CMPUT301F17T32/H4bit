package com.example.james.habitapp;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Arrays;

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
}