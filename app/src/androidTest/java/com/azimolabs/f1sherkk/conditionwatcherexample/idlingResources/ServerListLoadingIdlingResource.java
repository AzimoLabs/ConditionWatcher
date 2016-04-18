package com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.TestApplication;

/**
 * Created by F1sherKK on 14/04/16.
 */
public class ServerListLoadingIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle;

    @Override
    public String getName() {
        return ServerListLoadingIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        Activity activity = getCurrentActivity();
        if (activity == null) return false;

        SwipeRefreshLayout srItemList = (SwipeRefreshLayout) activity.findViewById(R.id.srItemList);
        isIdle = (srItemList != null && !srItemList.isRefreshing());
        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    public Activity getCurrentActivity() {
        return ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
    }

    @Override
    public void registerIdleTransitionCallback(
            ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
