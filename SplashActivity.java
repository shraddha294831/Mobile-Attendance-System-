package com.attendancesystem.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.attendancesystem.R;

public class SplashActivity extends BaseActivity {


    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        startLoginScreen();
    }

    private void startLoginScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                finish();
            }

        }, 3000);
    }
}
