package com.azimolabs.f1sherkk.conditionwatcherexample;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.azimolabs.conditionwatcher.ConditionWatcher;
import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;
import com.azimolabs.f1sherkk.conditionwatcherexample.instruction.BtnStartAnimationInstruction;
import com.azimolabs.f1sherkk.conditionwatcherexample.instruction.LoadingDialogInstruction;
import com.azimolabs.f1sherkk.conditionwatcherexample.instruction.ServerListLoadingInstruction;
import com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity.SplashActivity;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.DataProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ConditionWatcherExampleTests {

    @Rule
    public ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<>(SplashActivity.class, false, false);

    @Before
    public void setUp() {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void shouldDisplayServerDetails_conditionWatcher() throws Exception {
        List<Server> servers = DataProvider.generateServerList();
        Server thirdServer = servers.get(2);

        BtnStartAnimationInstruction btnStartAnimationInstruction = new BtnStartAnimationInstruction();
        ServerListLoadingInstruction serverListLoadingInstruction = new ServerListLoadingInstruction();
        LoadingDialogInstruction loadingDialogInstruction = new LoadingDialogInstruction();

        // Click on btnStart
        ConditionWatcher.waitForCondition(btnStartAnimationInstruction);
        onView(withId(R.id.btnStart)).perform(click());

        // Click on the 3rd item of listView
        ConditionWatcher.waitForCondition(serverListLoadingInstruction);
        onData(anything()).inAdapterView(withId(R.id.lvList)).atPosition(2).perform(click());

        // Assert details displayed
        ConditionWatcher.waitForCondition(loadingDialogInstruction);
        onView(withText(thirdServer.getName())).check(matches(isDisplayed()));
        onView(withText(thirdServer.getAddress())).check(matches(isDisplayed()));
        onView(withText(thirdServer.getPort())).check(matches(isDisplayed()));
    }
}