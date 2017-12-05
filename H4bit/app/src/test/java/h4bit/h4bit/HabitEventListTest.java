package h4bit.h4bit;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.Views.MainHabitActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
/**
 * HabitEventListTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */
public class HabitEventListTest{

    @Test
    public void testAddHabitEvent(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        User user1 = new User("user1");
        Habit habit = new Habit("name", "comment", sched,user1.getUsername());
        Location location =  createLocation(22.35, 65.679, 5.0f);
        HabitEvent habitEvent = new HabitEvent(habit,"comment",location);
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEvent, habitEventList.get(0));
    }

    @Test
    public void testIsDoneToday(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        User user1 = new User("user1");
        Habit habit = new Habit("name", "comment", sched,user1.getUsername());
        Location location =  createLocation(22.35, 65.679, 5.0f);
        HabitEvent habitEvent = new HabitEvent(habit,"comment",location);
        habitEventList.addHabitEvent(habitEvent);
        assertTrue(habitEventList.isDoneToday(habit));
    }
    @Test
    public void testSize(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        User user1 = new User("user1");
        Habit habit = new Habit("name", "comment", sched,user1.getUsername());
        Location location =  createLocation(22.35, 65.679, 5.0f);
        HabitEvent habitEvent = new HabitEvent(habit,"comment",location);
        habitEventList.addHabitEvent(habitEvent);
        assertEquals(habitEventList.size(), 1);
    }
    // Collections is built in so we dont need to test that its sorted
    // We can safely assume that it is
    @Test
    public void testDeleteHabitEvent(){
        HabitEventList habitEventList = new HabitEventList();
        boolean[] sched = new boolean[7];
        User user1 = new User("user1");
        Habit habit = new Habit("name", "comment", sched,user1.getUsername());
        Location location =  createLocation(22.35, 65.679, 5.0f);
        HabitEvent habitEvent = new HabitEvent(habit,"comment",location);
        habitEventList.addHabitEvent(habitEvent);
        habitEventList.deleteHabitEvent(habitEvent);
        assertEquals(habitEventList.size(), 0);
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

