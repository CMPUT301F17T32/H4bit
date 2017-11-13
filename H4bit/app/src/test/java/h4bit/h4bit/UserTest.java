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

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(MainHabitActivity.class);
    }

    public void testCreateUser(){
        User user1 = new User("user1");
        assertEquals(user1.getUsername(), "user1");

    }

    public void testSendFollowRequest(){
        User user1 = new User("user1");
        User user2 = new User ("user2");
        user1.sendFollowRequest("user2");
        boolean request = false;
//        if (user2.getRequests() == "user1"){
//            request = true;
//        }
        assertTrue(request);
    }
    public void testAddHabit(){
        boolean[] sched = new boolean[7];
        Habit habit = new Habit("name", "comment", sched);
        User user = new User("Name");
        user.addHabit(habit);
        assertTrue(user.getHabitList().hasHabit(habit));
    }
}
