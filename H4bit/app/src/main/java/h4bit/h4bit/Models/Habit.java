package h4bit.h4bit.Models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by James on 2017-10-20.
 */

public class Habit {

    private Date date;
    private String name;
    private String comment;
    private boolean[] schedule;
    private boolean doneToday;
    private int missed, completed;
    // We need a start date ???

    public Habit(String name, String comment, boolean[] schedule) {

        this.date = new Date();
        this.name = name;
        this.comment = comment;
        this.schedule = schedule;
        this.missed = 0;
        this.completed = 0;
        this.doneToday = false;

    }

    public double getCompletionRate() {
        if(getCompleted() + getMissed() == 0){
            return -1;
        } else {
            return (getCompleted() / (getMissed() + getCompleted())) * 100;
        }
    }

    public void doHabit(String comment, HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday();
        habitEventList.addHabitEvent(new HabitEvent(this, comment));
    }

    public void doHabit(HabitEventList habitEventList) {
        setCompleted(getCompleted() + 1);
        setDoneToday();
        habitEventList.addHabitEvent(new HabitEvent(this));
    }

    public int getNextDate(int dayNumber){
        int next = 0;

        if(!doneToday && this.getSchedule()[dayNumber]){
            return next;
        }
        next++;
        dayNumber++;

        for(int i = 0; i < 6; i++){
            if(this.getSchedule()[dayNumber]) {
                return next;
            }
            dayNumber++;
            if(dayNumber > 6){
                dayNumber -= 7;
            }
            next++;
        }
        return next;
    }

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

    public void setMissed(int missed) {
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

    public void setDoneToday(){
            this.doneToday = true;
    }

    public String toString(){
        return this.name;
    }

}
