package h4bit.h4bit.Models;

import java.util.Date;

/** User class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HabitEvent implements Comparable<HabitEvent> {

    private Habit habit;
    private Date date;
    //location stuff
    private String comment;

    /**
     *
     * @param habit
     */
    public HabitEvent(Habit habit) {
        this.habit = habit;
        this.date = new Date();
    }

    /**
     *
     * @param habit
     * @param comment
     */
    public HabitEvent(Habit habit, String comment) {
        this.habit = habit;
        this.date = new Date();
        this.comment = comment;
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
}
