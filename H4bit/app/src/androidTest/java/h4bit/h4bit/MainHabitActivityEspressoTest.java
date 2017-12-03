package h4bit.h4bit;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import h4bit.h4bit.Views.MainHabitActivity;

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

/**
 * Created by Vlad Kravchnko on 11/13/2017.
 */

public class MainHabitActivityEspressoTest {
    @Rule
    public IntentsTestRule<MainHabitActivity> mActivityRule =
            new IntentsTestRule<>(MainHabitActivity.class);

    @Test
    public void triggerIntentTest() {
        onView(withId(R.id.historyButton)).perform(click());
        Intent resultData = new Intent();
        String savefile = "file";
        resultData.putExtra("savefile", savefile);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("h4bit.h4bit.Views;")).respondWith(result);
    }

}


