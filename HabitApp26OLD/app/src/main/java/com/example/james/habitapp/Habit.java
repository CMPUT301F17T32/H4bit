package com.example.james.habitapp;

import java.util.Date;

/**
 * Created by James on 2017-10-20.
 */

public class Habit {
    private Date date;
    private String name;
    private String comment;
    private boolean[] schedule;

    public Habit(String name, String comment, boolean[] schedule) {

        this.date = new Date();
        this.name = name;
        this.comment = comment;
        this.schedule = schedule;
    }

    public double getCompletionRate() {
        return 0.0;
    }

    public void doHabit(String comment) {

    }

    public void doHabit() {

    }

    public void editSchedule(boolean[] schedule){

    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean[] getSchedule() {
        return schedule;
    }

    public void setSchedule(boolean[] schedule) {
        this.schedule = schedule;
    }
}
