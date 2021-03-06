package h4bit.h4bit;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import h4bit.h4bit.Views.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * LoginActivityEspressoTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.usernameText))
                .perform(typeText("User123456"), closeSoftKeyboard());;
        // Check that the text was changed.
        onView(withId(R.id.usernameText)).check(matches(withText("User123456")));

    }
    @Test
    public void failedLoginMainActivity() {
        onView(withId(R.id.usernameText)).perform(typeText("tesrtyre"),
                closeSoftKeyboard());
        onView(withId(R.id.crealogButton)).perform(click());
        onView(withId(R.id.usernameText)).check(matches(withText("tesrtyre")));
        //onView(withId(R.id.usernameText)).perform(typeText("st3"));
        //onView(withId(R.id.signupButton)).perform(click());


    }
    @Test
    public void login_MainHabitActivity() {
        // Type text and then press the button.
        String user = randomAN();
        onView(withId(R.id.usernameText)).perform(typeText(user),
                closeSoftKeyboard());
        onView(withId(R.id.signupButton)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.nameText)).perform(typeText("habit1"));
        onView(withId(R.id.nameText)).check(matches(withText("habit1")));
    }


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAN() {
        StringBuilder builder = new StringBuilder();
        int i = 5;
        while (i-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
