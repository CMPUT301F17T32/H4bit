package h4bit.h4bit;
import android.support.test.filters.LargeTest;
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
 * EditHabitEventTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

@RunWith(AndroidJUnit4.class)
public class EditHabitEventTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureEditEventWorks() {
        // Type text and then press the button.
        String user = randomAN();
        onView(withId(R.id.usernameText)).perform(typeText(user),
                closeSoftKeyboard());
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        // Type text and then press the button.
        onView(withId(R.id.nameText))
                .perform(typeText("Habit14"), closeSoftKeyboard());
        onView(withId(R.id.commentText))
                .perform(typeText("Comment1"), closeSoftKeyboard());
        onView(withId(R.id.mondayToggle)).perform(click());
        onView(withId(R.id.tuesdayToggle)).perform(click());
        onView(withId(R.id.wednesdayToggle)).perform(click());
        onView(withId(R.id.thursdayToggle)).perform(click());
        //onView(withId(R.id.crealogButton)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.habitsList)).atPosition(0).perform(click());
        onView(withId(R.id.doHabitButton)).perform(click());
        onView(withId(R.id.historyButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.eventsList)).atPosition(0).onChildView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.reasonText))
                .perform(typeText("CommentModified"), closeSoftKeyboard());
        // Check that the text was changed.
        //onView(withId(R.id.nameText)).check(matches(withText("Habit1424")));
        onView(withId(R.id.reasonText)).check(matches(withText("CommentModified")));
    }
    @Test

    public void ensureEditEventPassedToHistory() {
        // Type text and then press the button.
        String user = randomAN();
        onView(withId(R.id.usernameText)).perform(typeText(user),
                closeSoftKeyboard());
        onView(withId(R.id.signupButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        // Type text and then press the button.
        onView(withId(R.id.nameText))
                .perform(typeText("Habit14"), closeSoftKeyboard());
        onView(withId(R.id.commentText))
                .perform(typeText("Comment1"), closeSoftKeyboard());
        onView(withId(R.id.mondayToggle)).perform(click());
        onView(withId(R.id.tuesdayToggle)).perform(click());
        onView(withId(R.id.wednesdayToggle)).perform(click());
        onView(withId(R.id.thursdayToggle)).perform(click());
        //onView(withId(R.id.crealogButton)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.habitsList)).atPosition(0).perform(click());
        onView(withId(R.id.doHabitButton)).perform(click());
        onView(withId(R.id.historyButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.eventsList)).atPosition(0).onChildView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.reasonText))
                .perform(typeText("CommentModified"), closeSoftKeyboard());
        // Check that the text was changed.
        //onView(withId(R.id.nameText)).check(matches(withText("Habit1424")));
        onView(withId(R.id.reasonText)).check(matches(withText("CommentModified")));
        onView(withId(R.id.saveEventButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.habitsList)).atPosition(0);
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAN() {
        StringBuilder builder = new StringBuilder();
        int i = 5;
        while (i-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();

    }
}