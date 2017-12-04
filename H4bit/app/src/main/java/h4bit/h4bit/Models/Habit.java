package h4bit.h4bit.Models;

import android.location.Location;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import h4bit.h4bit.R;

/** Habit class
 * version 1.0
 * 2017-10-20.
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class Habit implements Comparable<Habit> {

    private Date startDate;
    private Date updatedDate;
    private String name;
    private String comment;
    private boolean[] schedule;
    private boolean doneToday;
    private int missed, completed, nextDate;
    private String id;

    /**
     * This creates the habit object with a startDate of today's date
     * @param name The name of the habit
     * @param comment The additional note/comment to add to the habit
     * @param schedule boolean array that stores the habit do dates
     */
    public Habit(String name, String comment, boolean[] schedule) {

        this.startDate = new Date();
        this.updatedDate = this.startDate;
        this.name = name;
        this.comment = comment;
        this.schedule = schedule;
        this.missed = 0;
        this.completed = 0;
        this.doneToday = false;
    }

    /**
     * This creates a habit with a custom startDate
     * @param name The name of the habit
     * @param comment The additional note/comment about the habit
     * @param schedule The boolean array schedule of when it should be completed
     * @param startDate The user selected custom start date
     */
    public Habit(String name, String comment, boolean[] schedule, Date startDate) {

        this.startDate = startDate;
        this.updatedDate = startDate;
        this.name = name;
        this.comment = comment;
        this.schedule = schedule;
        this.missed = 0;
        this.completed = 0;
        this.doneToday = false;

    }

    /**
<<<<<<< HEAD
     * Determines the number of days between two dates
     * @param d1
     * @param d2
     * @return
     */
    private int dayDifference(Date d1, Date d2){
        //float diff = d2.getTime()/86400000 - d1.getTime()/86400000;
        //int result = (int)diff;
        int result = d2.getDate() - d1.getDate();
        return result;
    }

    /**
     * This updates the statistics that are displayed to the user
     * @param habitEventList This is the habitEventList it uses to check if a date was missed etc.
     */
    public void updateStats(HabitEventList habitEventList){
        int numDays = dayDifference(this.getUpdatedDate(), new Date());
        Date theDate = this.getUpdatedDate();
        int updatedDay = this.getUpdatedDate().getDay();
        for(int i = 0; i < numDays; i++){
            updatedDay++;
            theDate.setTime(theDate.getTime() + 86400000);
            if(updatedDay > 6){
                updatedDay-= 7;
            }
            if(this.getSchedule()[updatedDay]){
                this.setMissed(this.getMissed() + 1);
                for(int j = 0; j < habitEventList.size(); j++){
                    if(habitEventList.get(j).getDate().getYear() == theDate.getYear() &&
                            habitEventList.get(j).getDate().getMonth() == theDate.getMonth() &&
                            habitEventList.get(j).getDate().getDate() == theDate.getDate() &&
                            habitEventList.get(j).getHabit().getName().equals(this.getName())){
                        this.setMissed(this.getMissed() - 1);
                        this.setCompleted(this.getCompleted() + 1);
                    }
                }
            }
        }
        this.setUpdatedDate(new Date());
    }

    /**
     * This computes the completion rate, how many times the habit was completed
     * @return Returns the completion rate statistic
     */
    public double getCompletionRate() {
        if(getCompleted() + getMissed() == 0){
            return -1;
        } else {
            Log.d("rate", String.valueOf(getCompleted()) + " " + String.valueOf(getMissed()));
            double result = (double)getCompleted() / ((double)getMissed() + (double)getCompleted());
            result *= 100;
            Log.d("rate result", String.valueOf(result));
            return result;
        }
    }

    /**
     * This creates a habitEvent based off the habit
     * @param comment Optional comment or note about completing the habit
     * @param location Location of where it was completed
     * @param habitEventList This is where the new habitEventObject is placed
     */
    public HabitEvent doHabit(String comment, Location location, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        updateStats(habitEventList);
        Date newDate = new Date();
        newDate.setTime(newDate.getTime() + 86400000);
        this.setUpdatedDate(newDate);
        HabitEvent theEvent = new HabitEvent(this, comment, location);
        habitEventList.addHabitEvent(theEvent);
        return theEvent;
    }

    /**
     * Creates a habit event based off habit without a comment
     * @param location location of where it was completed, can be null
     * @param habitEventList Where to place the new habitEvent
     */
    public HabitEvent doHabit(Location location, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        updateStats(habitEventList);
        Date newDate = new Date();
        newDate.setTime(newDate.getTime() + 86400000);
        this.setUpdatedDate(newDate);
        HabitEvent theEvent = new HabitEvent(this, location);
        habitEventList.addHabitEvent(theEvent);
        return theEvent;
    }

    /**
     * Determine's how many days until the next time a habit must be completed
     * @return nextDate
     */
    public int setNextDate(){
        Date currentDate = new Date();
        int untilStart = dayDifference(currentDate, this.getStartDate());
        Log.d("untilStart: ", String.valueOf(this.getStartDate()) + " " + String.valueOf(currentDate) + " " + untilStart);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1 + Math.max(untilStart, 0);
        //int todayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        this.nextDate = Math.max(untilStart, 0);
        while(dayNumber >= 7){
            dayNumber -= 7;
        }

        for(int i = 0; i < 7; i++){
            if(this.getSchedule()[dayNumber]){
                if(this.nextDate == 0){
                    if(!getDoneToday()){
                        return this.nextDate;
                    }
                } else {
                    return this.nextDate;
                }
            }
            dayNumber++;
            if(dayNumber >= 7){
                dayNumber -= 7;
            }
            this.nextDate++;
        }

        Log.d("NEVER", "plsno");
        return this.nextDate = -1;
        /*if(!getDoneToday() && this.getSchedule()[dayNumber] && untilStart < 1){
            return this.nextDate;
        }
        this.nextDate++;
        dayNumber++;
        if(dayNumber > 6){
            dayNumber -= 7;
        }

        for(int i = 0; i < 7; i++){
            if(this.getSchedule()[dayNumber]) {
                if(untilStart > this.nextDate) {
                    this.nextDate += 7;
                }
                return this.nextDate;
            }
            dayNumber++;
            if(dayNumber > 6){
                dayNumber -= 7;
            }
            this.nextDate++;
        }

        this.nextDate = Math.max(this.nextDate, untilStart);
        return this.nextDate;*/
    }

    /**
     * returns a string representation of a day of the week
     * @return date
     */
    public String getNextDayString(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int theNext = setNextDate();
        if(theNext == -1){
            return "N/A";
        } else if (theNext == 0){
            return "Today";
        } else if (theNext == 1){
            return "Tomorrow";
        } else {
            calendar.add(Calendar.DATE, theNext);
            return (new SimpleDateFormat("EEEE").format(calendar.getTime()));
        }
    }

    /**
     * used to implement comparable for sorting purposes
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
<<<<<<< HEAD
     * Edits the boolean schedule array
     * @param schedule boolean array to be edited
     */
    public void editSchedule(boolean[] schedule){
        setSchedule(schedule);
    }

    /**
     *
     * @return startdate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * removes events prior to the new start date, and updates stats
     * @param startDate new start date
     * @param habitEventList list of events
     */
    public void setStartDate(Date startDate, HabitEventList habitEventList){
        this.startDate = startDate;
        //Date today = new Date();
        for(int i = 0; i < habitEventList.size(); i++){
            if(habitEventList.get(i).getDate().getYear() < startDate.getYear() ||
                    habitEventList.get(i).getDate().getMonth() < startDate.getMonth() ||
                    habitEventList.get(i).getDate().getDate() < startDate.getDate() &&
                    habitEventList.get(i).getHabit().getName().equals(this.getName())){

                habitEventList.deleteHabitEvent(habitEventList.get(i));
                this.setCompleted(this.getCompleted() - 1);

            }
        }
        this.setDoneToday(habitEventList.isDoneToday(this));
        this.updateStats(habitEventList);
        this.setNextDate();
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

    private void setMissed(int missed){
        this.missed = missed;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Date getUpdatedDate() {
        return updatedDate;
    }

    private void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
