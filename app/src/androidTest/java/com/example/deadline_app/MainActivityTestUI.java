//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class MainActivityTestUI {
    String testLogin;

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
        testLogin = "test";
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
    @Test
    public void addNewTask(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.layout_new_task)).check(matches(isDisplayed()));
    }
    @Test
    public void calendarView(){
        onView(withId(R.id.calendarButton)).perform(click());
        intended(hasComponent(CalendarActivity.class.getName()));
    }
    @Test
    public void calendar_return(){
        onView(withId(R.id.calendarButton)).perform(click());
        intended(hasComponent(CalendarActivity.class.getName()));
        onView(withId(R.id.returnButton)).perform(click());
    }
    @Test
    public void loginButton(){
        onView(withId(R.id.button_main_login)).perform(click());
        intended(hasComponent(Login.class.getName()));
    }
    @Test
    public void loginTest() throws InterruptedException {
        onView(withId(R.id.button_main_login)).perform(click());
        intended(hasComponent(Login.class.getName()));
        onView(withId(R.id.login_phone_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.login_password_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.tasksText)).check(matches(isDisplayed()));
    }
    @Test
    public void userMenuTest() throws InterruptedException {
        onView(withId(R.id.button_main_login)).perform(click());
        intended(hasComponent(Login.class.getName()));
        onView(withId(R.id.login_phone_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.login_password_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.button_main_user)).perform(click());
        onView(withId(R.id.dialog_username)).check(matches(isDisplayed()));

    }
    @Test
    public void logoutTest() throws InterruptedException {
        onView(withId(R.id.button_main_login)).perform(click());
        intended(hasComponent(Login.class.getName()));
        onView(withId(R.id.login_phone_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.login_password_textField))
                .perform(typeText(testLogin), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.button_main_user)).perform(click());
        onView(withId(R.id.dialog_button_logout)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.layout_login)).check(matches(isDisplayed()));

    }
}
