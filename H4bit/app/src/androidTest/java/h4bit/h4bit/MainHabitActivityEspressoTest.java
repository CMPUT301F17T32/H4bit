package h4bit.h4bit;

/**
 * Created by Vlad Kravchnko on 11/13/2017.
 */

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import h4bit.h4bit.Views.LoginActivity;
import h4bit.h4bit.Views.MainHabitActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.CoreMatchers.anything;

/**
 * MainHabitActivityEspressoTest
 * Version 1.0
 * december 1st 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class MainHabitActivityEspressoTest {
    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void transferToHistory() {
        login();
        onView(withId(R.id.historyButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.eventsList)).atPosition(0);
        //onView(withId(R.id.AutoCompleteName))
        //      .perform(typeText("Habit1345"), closeSoftKeyboard());
        //onView(withId(R.id.searchButton)).perform(click());

    }
    @Test
    public void transferToSocial(){
        login();
        onView(withId(R.id.socialButton)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.habitStatusList)).atPosition(0);

    }
    public void login() {
        // Type text and then press the button.
        String user = randomAN();
        onView(withId(R.id.usernameText)).perform(typeText(user),
                closeSoftKeyboard());
        onView(withId(R.id.signupButton)).perform(click());
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

