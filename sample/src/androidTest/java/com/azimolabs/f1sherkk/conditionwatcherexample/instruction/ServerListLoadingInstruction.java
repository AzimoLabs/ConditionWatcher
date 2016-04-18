package com.azimolabs.f1sherkk.conditionwatcherexample.instruction;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.v4.widget.SwipeRefreshLayout;

import com.azimolabs.conditionwatcher.Instruction;
import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.TestApplication;

/**
 * Created by F1sherKK on 15/04/16.
 */
public class ServerListLoadingInstruction extends Instruction {

    @Override
    public String getDescription() {
        return "SwipeRefreshLayout should finish refreshing and disappear";
    }

    @Override
    public boolean checkCondition() {
        Activity activity = ((TestApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        if (activity == null) return false;

        SwipeRefreshLayout srItemList = (SwipeRefreshLayout) activity.findViewById(R.id.srItemList);
        return srItemList != null && !srItemList.isRefreshing();
    }
}
