package com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;
import com.azimolabs.f1sherkk.conditionwatcherexample.ui.adapter.ServerAdapter;
import com.azimolabs.f1sherkk.conditionwatcherexample.ui.dialog.LoadingDialog;
import com.azimolabs.f1sherkk.conditionwatcherexample.utils.DataProvider;

import java.util.Random;

/**
 * Created by F1sherKK on 08/04/16.
 */
public class ListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lvItems;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ServerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bindViews();

        setupAdapter();
        setupListView();
        setupSwipeRefreshLayout();

        loadDataWithDelay(generateDelay(1000, 5000));
    }

    private void bindViews() {
        lvItems = (ListView) findViewById(R.id.lvList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srItemList);
    }

    private void setupAdapter() {
        adapter = new ServerAdapter(this);
    }

    private void setupListView() {
        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListActivity.this.onItemClick(adapter.getItem(position));
            }
        });
    }

    public void onItemClick(Server serverItem) {
        String message = generateLoadingMessage(serverItem.getAddress(), serverItem.getPort());
        showProgress(message);

        startServerDetailsDelayed(serverItem, generateDelay(1000, 3000));
    }

    public void showProgress(final String message) {
        new Handler(Looper.getMainLooper()).post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            hideProgress();
                            LoadingDialog loadingDialog = LoadingDialog.newInstance(message);
                            loadingDialog.setCancelable(false);
                            loadingDialog.show(getFragmentManager(), LoadingDialog.TAG);
                        }
                    }
                }
        );
    }

    public void hideProgress() {
        new Handler(Looper.getMainLooper()).post(
                new Runnable() {
                    @Override
                    public void run() {
                        DialogFragment loadingDialog =
                                (DialogFragment) getFragmentManager().findFragmentByTag(LoadingDialog.TAG);
                        if (loadingDialog != null) {
                            loadingDialog.dismissAllowingStateLoss();
                        }
                    }
                }
        );
    }

    public void startServerDetailsDelayed(final Server serverItem, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();

                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.KEY_SERVER, serverItem);
                ListActivity.this.startActivity(intent);
            }
        }, delay);
    }

    public int generateDelay(int minValueInMillis, int maxValueInMillis) {
        return new Random().nextInt(maxValueInMillis - minValueInMillis) + minValueInMillis;
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    public void loadDataWithDelay(int delayInMillis) {
        showLoadingData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(DataProvider.generateServerList());
                hideLoadingData();
            }
        }, delayInMillis);
    }

    public void showLoadingData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void hideLoadingData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadDataWithDelay(generateDelay(1000, 5000));
    }

    public String generateLoadingMessage(String address, String port) {
        return String.format(getString(R.string.server_loading), address, port);
    }
}
