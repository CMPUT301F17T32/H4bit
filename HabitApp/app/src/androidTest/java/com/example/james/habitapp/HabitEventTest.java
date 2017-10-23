package com.example.james.habitapp;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Arrays;

/**
 * Created by Vlad Kravchnko on 10/22/2017.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {

    public HabitEventTest() {
        super(MainHabitActivity.class);
    }
    public void testCreateHabitEvent(){
        String comment = "test comment for event";
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched);
        HabitEvent event1 = new HabitEvent(habit1,comment);
        assertEquals(event1.getHabit(),habit1);
        assertEquals(event1.getComment(),comment);
    }
}
