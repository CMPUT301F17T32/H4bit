package h4bit.h4bit.Models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import h4bit.h4bit.R;

/** User class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class Habit implements Comparable<Habit> {

    private Date date;
    private String name;
    private String comment;
    private boolean[] schedule;
    private boolean doneToday;
    private int missed, completed, nextDate;

    /**
     *
     * @param name
     * @param comment
     * @param schedule
     */
    public Habit(String name, String comment, boolean[] schedule) {

        this.date = new Date();
        this.name = name;
        this.comment = comment;
        this.schedule = schedule;
        this.missed = 0;
        this.completed = 0;
        this.doneToday = false;

    }

    /**
     *
     * @return completionRate
     */
    public double getCompletionRate() {
        if(getCompleted() + getMissed() == 0){
            return -1;
        } else {
            return (getCompleted() / (getMissed() + getCompleted())) * 100;
        }
    }

    /**
     *
     * @param comment
     * @param habitEventList
     */
    public void doHabit(String comment, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        habitEventList.addHabitEvent(new HabitEvent(this, comment));
    }

    /**
     *
     * @param habitEventList
     */
    public void doHabit(HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        habitEventList.addHabitEvent(new HabitEvent(this));
    }

    /**
     *
     * @return nextDate
     */
    public int setNextDate(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        this.nextDate = 0;

        if(!getDoneToday() && this.getSchedule()[dayNumber]){
            return this.nextDate;
        }
        this.nextDate++;
        dayNumber++;
        if(dayNumber > 6){
            dayNumber -= 7;
        }

        for(int i = 0; i < 7; i++){
            if(this.getSchedule()[dayNumber]) {
                return this.nextDate;
            }
            dayNumber++;
            if(dayNumber > 6){
                dayNumber -= 7;
            }
            this.nextDate++;
        }

        return this.nextDate;
    }

    /**
     *
     * @return date
     */
    public String getNextDayString(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int theNext = setNextDate();
        if(theNext == 0){
            return "Today";
        } else if (theNext == 1){
            return "Tomorrow";
        } else {
            calendar.add(Calendar.DATE, theNext);
            return (new SimpleDateFormat("EEEE").format(calendar.getTime()));
        }
    }

    /**
     *
     * @param compareHabit habit you want to compare
     * @return nextDate
     */
    @Override
    public int compareTo(Habit compareHabit) {

        int compareQuantity = compareHabit.getNextDate();
        //ascending order
        return this.getNextDate() - compareQuantity;

    }

    /**
     *
     * @param schedule
     */
    public void editSchedule(boolean[] schedule){
        setSchedule(schedule);
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

    public int getMissed() {
        return missed;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public boolean getDoneToday(){
        return this.doneToday;
    }

    public void setDoneToday(boolean b){
            this.doneToday = b;
    }

    public int getNextDate(){
        return this.nextDate;
    }

    public String toString(){
        return this.name;
    }

}
