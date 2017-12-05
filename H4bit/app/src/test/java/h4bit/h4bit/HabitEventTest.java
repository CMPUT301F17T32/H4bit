package h4bit.h4bit;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Views.MainHabitActivity;

import static org.junit.Assert.*;
/**
 * HabitEventTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */
public class HabitEventTest{

    @Test
    public void testNoImage(){

        String comment = "test comment for event";
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Location location =  createLocation(22.35, 65.679, 5.0f);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched,"user1");
        HabitEvent event1 = new HabitEvent(habit1,comment,location);
        boolean test = false;
        if (event1.getImage()==null){
            test=true;
        }
        assertTrue(test);

    }

    @Test
    public void testLocation(){

        String comment = "test comment for event";
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Location location =  createLocation(22.35, 65.679, 5.0f);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched,"user1");
        HabitEvent event1 = new HabitEvent(habit1,comment,location);
        boolean test = false;
        if (event1.getLocation()==location){
            test=true;
        }
        assertTrue(test);
    }


    @Test
    public void testCreateHabitEvent(){
        String comment = "test comment for event";
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Location location =  createLocation(22.35, 65.679, 5.0f);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched,"user1");
        HabitEvent event1 = new HabitEvent(habit1,comment,location);
        assertEquals(event1.getHabit(),habit1);
        assertEquals(event1.getComment(),comment);
    }
    @Test
    public void testGetHabitEvent(){
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Location location =  createLocation(22.35, 65.679, 5.0f);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched,"user1");
        HabitEvent habitEvent = new HabitEvent(habit1,"comment",location);
        assertEquals(habitEvent.getHabit(), habit1);
    }
    @Test
    public void testGetComment(){
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        Location location =  createLocation(22.35, 65.679, 5.0f);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched,"user1");
        HabitEvent habitEvent = new HabitEvent(habit1,"test3",location);
        assertEquals(habitEvent.getComment(), "test3");
    }
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location("PROVIDER");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
    // compareto does not testing as its another built in method with expected behaviour
}
