package h4bit.h4bit;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.Views.MainHabitActivity;

import static org.junit.Assert.*;
/**
 * Created by Vlad Kravchnko on 10/22/2017.
 */

public class UserTest {

    @Test
    public void testCreateUser(){
        User user1 = new User("user1");
        assertEquals(user1.getUsername(), "user1");

    }

    @Test
    public void testAddHabit(){
        boolean[] sched = new boolean[7];
        User user1 = new User("user1");
        Habit habit = new Habit("name", "comment", sched, user1.getUsername());
        User user = new User("Name");
        user.addHabit(habit);
        assertTrue(user.getHabitList().hasHabit(habit));
    }
}
