package com.azimolabs.f1sherkk.conditionwatcherexample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;

public class SplashActivity extends AppCompatActivity {

    public static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindViews();
        setupListeners();

        initBtnStartAnimation();
    }

    private void bindViews() {
        btnStart = (Button)findViewById(R.id.btnStart);
    }

    private void setupListeners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });
    }

    private void startListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void initBtnStartAnimation() {
        btnStart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnStart.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float initialTranslationY = -1 * (btnStart.getY() + btnStart.getHeight());
                btnStart.setTranslationY(initialTranslationY);

                btnStart.setVisibility(View.VISIBLE);
                btnStart.animate()
                        .translationY(0)
                        .setDuration(800)
                        .setInterpolator(DECELERATE_INTERPOLATOR)
                        .setStartDelay(1000);
            }
        });
    }
}
