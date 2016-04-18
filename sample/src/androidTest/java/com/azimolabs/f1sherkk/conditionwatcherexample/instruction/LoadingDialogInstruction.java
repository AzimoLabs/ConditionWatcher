package com.azimolabs.f1sherkk.conditionwatcherexample.instruction;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.test.InstrumentationRegistry;

import com.azimolabs.conditionwatcher.Instruction;
import com.azimolabs.f1sherkk.conditionwatcherexample.ui.dialog.LoadingDialog;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.TestApplication;

/**
 * Created by F1sherKK on 15/04/16.
 */
public class LoadingDialogInstruction extends Instruction {
    @Override
    public String getDescription() {
        return "Loading dialog shouldn't be in view hierarchy";
    }

    @Override
    public boolean checkCondition() {
        Activity activity = ((TestApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        if (activity == null) return false;

        DialogFragment f = (DialogFragment) activity.getFragmentManager().findFragmentByTag(LoadingDialog.TAG);
        return f == null;
    }
}
