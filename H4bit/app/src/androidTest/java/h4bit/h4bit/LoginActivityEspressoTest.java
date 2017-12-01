package h4bit.h4bit;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import h4bit.h4bit.Views.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.statusUsernameText))
                .perform(typeText("User1"), closeSoftKeyboard());
        onView(withId(R.id.passwordText))
                .perform(typeText("pswd1"), closeSoftKeyboard());
        onView(withId(R.id.crealogButton)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.statusUsernameText)).check(matches(withText("User1")));
        onView(withId(R.id.passwordText)).check(matches(withText("pswd1")));
    }

    @Test
    public void login_MainHabitActivity() {
        // Type text and then press the button.
        onView(withId(R.id.statusUsernameText)).perform(typeText("User1"),
                closeSoftKeyboard());
        onView(withId(R.id.passwordText)).perform(typeText("pswd1"),
                closeSoftKeyboard());


        onView(withId(R.id.crealogButton)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        // onView(withId(R.id.)).check(matches(withText("NewText")));
    }
}

