package h4bit.h4bit;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Arrays;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.HabitList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.Views.MainHabitActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
/**
 * HabitTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */
public class HabitTest{

    @Test
    public void testCreateHabit() {
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        String name = "name1";
        String comment = "comment1";
        User user1 = new User();
        Habit habit1 = new Habit("name1", "comment1", sched,user1.getUsername());
        assertEquals(habit1.getName(), name);
        assertEquals(habit1.getComment(),comment);
        assertEquals(habit1.getSchedule(), sched);
    }
    @Test
     public void testEditSchedule(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         User user1 = new User();
         Habit habit1 = new Habit("name1", "comment1", sched, user1.getUsername());
         boolean[] sched1 = new boolean[7];
         sched1[0]=false;
         habit1.editSchedule(sched1);
         boolean [] schedTest= habit1.getSchedule();
         assertFalse(schedTest[0]);
     }

    @Test
     public void testGetCompletionRate(){
         HabitEventList habitEventList = new HabitEventList();
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         User user1 = new User();
         Habit habit = new Habit("name", "comment", sched,user1.getUsername());
         habit.setStartDateTest();
         Location location = createLocation(22.35, 65.679, 5.0f);
         habit.doHabit(location, habitEventList);
         String testmessage = String.valueOf(habit.getCompletionRate());
         //assertTrue(testmessage,true);
         assertEquals(habit.getCompletionRate(), 100.0);
     }
    @Test
     public void testSetNextDate(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         User user1 = new User();
         Habit habit = new Habit("name", "comment", sched, user1.getUsername());
         habit.setStartDateTest();
         int date = habit.getNextDate();
         habit.setNextDate();
         assertEquals(habit.getNextDate(), date);
     }
    @Test
     public void testGetNextDayString(){
         boolean[] sched = new boolean[7];
         Arrays.fill(sched, true);
         User user1 = new User();

         Habit habit = new Habit("name", "comment", sched, user1.getUsername());
         habit.setStartDateTest();
         assertEquals(habit.getNextDayString(), "Today");
         habit.setNextDate();
         //assertEquals(habit.getNextDayString(), "Tomorrow");
     }
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location("PROVIDER");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }

}