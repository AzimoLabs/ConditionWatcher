package com.azimolabs.f1sherkk.conditionwatcherexample.idlingResources;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;

import com.azimolabs.f1sherkk.conditionwatcherexample.ui.dialog.LoadingDialog;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.TestApplication;

/**
 * Created by F1sherKK on 14/04/16.
 */
public class LoadingDialogIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle;

    @Override
    public String getName() {
        return LoadingDialogIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        Activity activity = getCurrentActivity();
        if (activity == null) return false;

        DialogFragment f = (DialogFragment) activity.getFragmentManager().findFragmentByTag(LoadingDialog.TAG);
        isIdle = f == null;
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
