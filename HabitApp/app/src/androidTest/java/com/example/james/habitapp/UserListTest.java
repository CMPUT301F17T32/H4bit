package com.example.james.habitapp;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by James on 2017-10-20.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){
        super (MainHabitActivity.class);
    }

    public void testAddUser(){
        UserList users = new UserList();
        User user1 = new User("idNumberOne");
        users.addUser(user1);
        assertTrue(users.hasUser(user1));

    }

    private void testHasUser(){
        UserList users = new UserList();
        User user1 = new User("idNumberOne");
        assertFalse(users.hasUser(user1));

        users.addUser(user1);
        assertTrue(users.hasUser(user1));

    }
    private void testGetUser(){
        UserList users = new UserList();
        User user1 = new User("idNumberOne");
        users.addUser(user1);

        User returnedUser = UserList.getUser(0);
        assertEquals(user1.getId, returnedUser.getId);

    }

}
