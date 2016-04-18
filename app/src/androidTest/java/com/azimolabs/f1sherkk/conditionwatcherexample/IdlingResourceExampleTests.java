package com.azimolabs.f1sherkk.conditionwatcherexample;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;
import com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources.BtnStartAnimationIdlingResource;
import com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources.LoadingDialogIdlingResource;
import com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources.ServerListLoadingIdlingResource;
import com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity.SplashActivity;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.DataProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceExampleTests {

    @Rule
    public ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<>(SplashActivity.class, false, false);

    @Before
    public void setUp() {
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void shouldDisplayServerDetails_idlingResource() throws Exception {
        List<Server> servers = DataProvider.generateServerList();
        Server thirdServer = servers.get(2);

        IdlingPolicies.setMasterPolicyTimeout(
                1000 * 30, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(
                1000 * 30, TimeUnit.MILLISECONDS);

        BtnStartAnimationIdlingResource btnStartAnimationIdlingResource = new BtnStartAnimationIdlingResource();
        ServerListLoadingIdlingResource serverListLoadingIdlingResource = new ServerListLoadingIdlingResource();
        LoadingDialogIdlingResource loadingDialogIdlingResource = new LoadingDialogIdlingResource();

        // Click on btnStart
        Espresso.registerIdlingResources(btnStartAnimationIdlingResource);
        onView(withId(R.id.btnStart)).perform(click());
        Espresso.unregisterIdlingResources(btnStartAnimationIdlingResource);

        // Click on the 3rd item of listView
        Espresso.registerIdlingResources(serverListLoadingIdlingResource);
        onData(anything()).inAdapterView(withId(R.id.lvList)).atPosition(2).perform(click());
        Espresso.registerIdlingResources(serverListLoadingIdlingResource);

        // Assert details displayed
        Espresso.registerIdlingResources(loadingDialogIdlingResource);
        onView(withText(thirdServer.getName())).check(matches(isDisplayed()));
        onView(withText(thirdServer.getAddress())).check(matches(isDisplayed()));
        onView(withText(thirdServer.getPort())).check(matches(isDisplayed()));
        Espresso.registerIdlingResources(serverListLoadingIdlingResource);
    }
}