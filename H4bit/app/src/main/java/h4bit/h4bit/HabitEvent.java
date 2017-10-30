package h4bit.h4bit;

import java.util.Date;

/**
 * Created by James on 2017-10-20.
 */

public class HabitEvent {

    private Habit habit;
    private Date date;
    //location stuff
    private String comment;

    public HabitEvent(Habit habit) {
        this.habit = habit;
        this.date = new Date();
    }
    public HabitEvent(Habit habit, String comment) {
        this.habit = habit;
        this.date = new Date();
        this.comment = comment;
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
