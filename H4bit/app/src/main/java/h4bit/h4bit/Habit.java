package h4bit.h4bit;

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
        return getCompleted() / (getMissed() + getCompleted());
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

}
