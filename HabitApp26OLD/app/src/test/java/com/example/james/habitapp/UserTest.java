package com.example.james.habitapp;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Vlad Kravchnko on 10/22/2017.
 */

public class UserTest{
    @Test
    public void testCreateUser(){
        User user1 = new User("user1");
        assertEquals(user1.getUsername, "user1");

    }

    public void testSendFollowRequest(){
        User user1 = new User("user1");
        User user2 = new User ("user2");
        user1.sendFollowRequest("user2");
        boolean request = false;
        if (user2.getRequests() == "user1"){
            request = true;
        }
        assertTrue(request);
    }

}
