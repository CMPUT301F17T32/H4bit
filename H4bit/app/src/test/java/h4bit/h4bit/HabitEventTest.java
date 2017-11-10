package h4bit.h4bit;
import java.util.Arrays;
import org.junit.Test;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;

import static org.junit.Assert.*;

/**
 * Created by Vlad Kravchnko on 10/22/2017.
 */

public class HabitEventTest{

    @Test
    public void testCreateHabitEvent(){
        String comment = "test comment for event";
        boolean[] sched = new boolean[7];
        Arrays.fill(sched, true);
        String habitsComment = "this is comment of test habit";
        Habit habit1 = new Habit("habit1",habitsComment,sched);
        HabitEvent event1 = new HabitEvent(habit1,comment);
        assertEquals(event1.getHabit(),habit1);
        assertEquals(event1.getComment(),comment);
    }
}
