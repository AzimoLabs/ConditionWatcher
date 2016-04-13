package com.azimolabs.f1sherkk.conditionwatcherexample.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by F1sherKK on 13/04/16.
 */
public class LoadingDialog extends DialogFragment {
    public static final String TAG = "LoadingDialog";

    private static final String ARG_MESSAGE = "ARG_MESSAGE";
    private static final String ARG_IS_FROM_STRING = "ARG_IS_FROM_STRING";

    private String message;

    public static LoadingDialog newInstance(String message) {
        LoadingDialog dialog = new LoadingDialog();

        final Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putBoolean(ARG_IS_FROM_STRING, true);

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getArguments().getString(ARG_MESSAGE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        return progressDialog;
    }
}

