//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

/*
        Create Account Test
        All text fields including phone, username and password are tested with auto typing and checking.
        Also, espresso has tested the interaction with all buttons in create account page and login screen,
        the expected activity can launch normally.

 */


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateAcTestUI {
    private String testString;
    private int rand;

    @Rule
    public ActivityScenarioRule<CreateAc> activityScenarioRule = new ActivityScenarioRule<CreateAc>(CreateAc.class);

    @Before
    public void setUp() throws Exception {
        rand = new Random().nextInt(10000)+100;
        testString = "test";
        Intents.init();
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
    @Test
    public void textInput(){
        // Type text and then press the button.
        onView(withId(R.id.create_phone_textField))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.create_username_textField))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.create_password_textField))
                .perform(typeText(testString), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.create_phone_textField))
                .check(matches(withText(testString)));
        onView(withId(R.id.create_username_textField))
                .check(matches(withText(testString)));
        onView(withId(R.id.create_password_textField))
                .check(matches(withText(testString)));
    }
    @Test
    public void createAccount(){
        onView(withId(R.id.create_phone_textField))
                .perform(typeText(Integer.toString(rand)), closeSoftKeyboard());
        onView(withId(R.id.create_username_textField))
                .perform(typeText(Integer.toString(rand)), closeSoftKeyboard());
        onView(withId(R.id.create_password_textField))
                .perform(typeText(Integer.toString(rand)), closeSoftKeyboard());

        onView(withId(R.id.button_create_ac)).perform(click());
    }
    @Test
    public void toLoginPage() throws InterruptedException {
        onView(withId(R.id.toLogin)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.layout_login)).check(matches(isDisplayed()));
    }




    //"Phone number already exist"

}