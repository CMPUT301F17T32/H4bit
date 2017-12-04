package h4bit.h4bit;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import h4bit.h4bit.Views.CreateHabitActivity;
import h4bit.h4bit.Views.LoginActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

/**
 * EditHabitActivityEspressoTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */
@RunWith(AndroidJUnit4.class)
public class EditHabitEspressoTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.usernameText)).perform(typeText("test3"),
                closeSoftKeyboard());
        onView(withId(R.id.crealogButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.nameText))
                .perform(typeText("Habit1"), closeSoftKeyboard());
        onView(withId(R.id.commentText))
                .perform(typeText("Comment1"), closeSoftKeyboard());
        onView(withId(R.id.mondayToggle)).perform(click());
        onView(withId(R.id.tuesdayToggle)).perform(click());
        //onView(withId(R.id.crealogButton)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.habitsList)).atPosition(0);

        // Check that the text was changed.
        //onView(withId(R.id.nameText)).check(matches(withText("Habit1")));
        //onView(withId(R.id.commentText)).check(matches(withText("Comment1")));
    }
}
