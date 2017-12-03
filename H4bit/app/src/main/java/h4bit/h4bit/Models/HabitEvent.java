package h4bit.h4bit.Models;

import android.location.Location;

import java.util.Date;

/** User class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitEvent implements Comparable<HabitEvent> {

    private Habit habit;
    private Date date;
    private Location location;
    private String comment;

    /**
     *
     * @param habit habit
     */
    public HabitEvent(Habit habit, Location location) {
        this.habit = habit;
        this.date = new Date();
        this.location = location;
        this.comment="";
    }

    /**
     *
     * @param habit habit
     * @param comment comment
     */
    public HabitEvent(Habit habit, String comment, Location location) {
        this.habit = habit;
        this.date = new Date();
        this.comment = comment;
        this.location = location;
    }

    public int compareTo(HabitEvent compareHabitEvent) {

        Date compareQuantity = compareHabitEvent.getDate();
        //ascending order
        return compareQuantity.compareTo(this.getDate());

    }

    public Habit getHabit() {
        return habit;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Location getLocation(){return this.location;}
}
