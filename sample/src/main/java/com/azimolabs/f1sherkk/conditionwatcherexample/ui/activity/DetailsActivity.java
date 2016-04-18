package com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;

/**
 * Created by F1sherKK on 08/04/16.
 */
public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_SERVER = "KeyServer";

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPort;

    private Server server;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bindViews();
        initData(savedInstanceState);
        loadData();
    }

    private void bindViews() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPort = (TextView) findViewById(R.id.tvPort);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null && getIntent().hasExtra(KEY_SERVER)) {
            server = (Server) getIntent().getExtras().get(KEY_SERVER);
        }
    }

    private void loadData() {
        if (server == null) return;

        tvName.setText(server.getName());
        tvAddress.setText(server.getAddress());
        tvPort.setText(server.getPort());
    }
}
