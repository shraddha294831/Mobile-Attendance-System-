package com.attendancesystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.attendancesystem.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by shraddha on 3/6/2018.
 */

public abstract class BaseActivity extends Activity {

    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        progressDialog = new ProgressDialog(this, R.style.CustomProgressDialogTheme);

        initData();

        initViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public abstract int getContentView();

    public abstract void initData();

    public abstract void initViews();


    public void showProgress(String message) {
        progressDialog.setMessage("");
        progressDialog.getWindow()
                .setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
