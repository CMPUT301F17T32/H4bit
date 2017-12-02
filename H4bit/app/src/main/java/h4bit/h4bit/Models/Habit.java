package h4bit.h4bit.Models;

import android.location.Location;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import h4bit.h4bit.R;

/** User class
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
     *
     * @param name
     * @param comment
     * @param schedule
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

    private int dayDifference(Date d1, Date d2){
        long diff = d2.getTime() - d1.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void updateStats(HabitEventList habitEventList){
        int numDays = dayDifference(new Date(), this.getUpdatedDate());
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
                            Objects.equals(habitEventList.get(j).getHabit(), this)){
                        this.setMissed(this.getMissed() - 1);
                        this.setCompleted(this.getCompleted() + 1);
                    }
                }
            }
        }
        this.setUpdatedDate(new Date());
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
     * @param location
     * @param habitEventList
     */
    public void doHabit(String comment, Location location, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        updateStats(habitEventList);
        Date newDate = new Date();
        newDate.setTime(newDate.getTime() + 86400000);
        this.setUpdatedDate(newDate);
        habitEventList.addHabitEvent(new HabitEvent(this, comment, location));
    }

    /**
     *
     * @param habitEventList
     */
    public void doHabit(Location location, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday(true);
        setNextDate();
        updateStats(habitEventList);
        Date newDate = new Date();
        newDate.setTime(newDate.getTime() + 86400000);
        this.setUpdatedDate(newDate);
        habitEventList.addHabitEvent(new HabitEvent(this, location));
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate, HabitEventList habitEventList){
        this.startDate = startDate;
        for(int i = 0; i < habitEventList.size(); i++){
            if(habitEventList.get(i).getDate().getYear() + 1900 < startDate.getYear() ||
                    habitEventList.get(i).getDate().getMonth() < startDate.getMonth() ||
                    habitEventList.get(i).getDate().getDate() < startDate.getDate() &&
                    Objects.equals(habitEventList.get(i).getHabit(), this)){
                habitEventList.deleteHabitEvent(habitEventList.get(i));
            }
        }
        this.setCompleted(0);
        this.setMissed(0);
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

    public void setMissed(int missed){
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
