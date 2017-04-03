package com.azimolabs.f1sherkk.conditionwatcherexample;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity.SplashActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IdleStateTests {

    @Rule
    public ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, false);

    @Before
    public void setUp() {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void test1() throws Exception {
        onView(withId(R.id.btnButton1)).perform(click());
    }

    @Test
    public void test2() throws Exception {
        onView(withId(R.id.btnButton2)).perform(click());
        onView(withId(R.id.btnButton1)).perform(click());
    }
}