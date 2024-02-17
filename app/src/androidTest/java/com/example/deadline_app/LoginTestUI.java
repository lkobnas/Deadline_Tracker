//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.assertNoUnverifiedIntents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

/*
        Login Test
        All text fields including phone and password are tested with auto typing and checking.
        Also, espresso has tested the interaction with all buttons including switching the create
        a new account and continue without login, the expected activity can launch normally.

 */


@RunWith(AndroidJUnit4.class)
public class LoginTestUI {
    private String testLogin;

    @Rule
    public ActivityScenarioRule<Login> rule = new ActivityScenarioRule<Login>(Login.class);

    @Before
    public void setUp() throws Exception {
        testLogin = "test";
        Intents.init();
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void textInput(){
        // Type text and then press the button.
        onView(withId(R.id.login_phone_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.login_password_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.login_phone_textField))
                .check(matches(withText(testLogin)));
        onView(withId(R.id.login_password_textField))
                .check(matches(withText(testLogin)));
    }
    @Test
    public void withLogin(){
        onView(withId(R.id.login_phone_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.login_password_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
    }
    @Test
    public void withoutLogin(){
        onView(withId(R.id.noLogin)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

}