package com.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.attendancesystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srujan on 3/18/2018.
 */

public class DashboardActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cvManageStudents)
    CardView cvManageStudents;
    @BindView(R.id.cvAttendance)
    CardView cvAttendance;
    @BindView(R.id.cvAttendanceHistory)
    CardView cvAttendanceHistory;
    @BindView(R.id.cvUnits)
    CardView cvUnits;


    @Override
    public int getContentView() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        cvManageStudents.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, StudentListActivity.class);
            startActivity(i);
        });

        cvAttendance.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, AttendanceDetailsActivity.class);
            startActivity(i);
        });

        cvAttendanceHistory.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, AttendanceHistoryActivity.class);
            startActivity(i);
        });

        cvUnits.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, UnitListActivity.class);
            startActivity(i);
        });
    }

}
