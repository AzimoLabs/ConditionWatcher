package com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.widget.Button;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.TestApplication;

/**
 * Created by F1sherKK on 14/04/16.
 */
public class BtnStartAnimationIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle;

    @Override
    public String getName() {
        return BtnStartAnimationIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        Activity activity = getCurrentActivity();
        if (activity == null) return false;

        Button btnStart = (Button) activity.findViewById(R.id.btnStart);
        isIdle = (btnStart != null && btnStart.getTranslationY() == 0);
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
