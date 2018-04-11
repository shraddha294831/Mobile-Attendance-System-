package com.attendancesystem.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.adapter.AttendanceHistoryListAdapter;
import com.attendancesystem.adapter.AttendanceListAdapter;
import com.attendancesystem.bean.AttendanceBean;
import com.attendancesystem.bean.StudentBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shraddha on 3/25/2018.
 */

public class AttendanceHistoryActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvAttendanceHistory)
    RecyclerView rvAttendanceHistory;

    private ArrayList<AttendanceBean> arrAttendance;
    private AttendanceHistoryListAdapter attendanceListAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_attendance_history;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Attendance History");
        rvAttendanceHistory.setHasFixedSize(true);
        rvAttendanceHistory.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
    }

    private ArrayList<AttendanceBean> generateAttendanceList() {
        arrAttendance = new ArrayList<>();
        AttendanceBean bean;

        for (int i = 0; i <= 20; i++) {
            bean = new AttendanceBean();
            bean.setDate("12/12/2018");
            bean.setSubject("Subject " + i);

            arrAttendance.add(bean);
        }
        return arrAttendance;
    }

    private void initAdapter() {
        attendanceListAdapter = new AttendanceHistoryListAdapter(generateAttendanceList());
        attendanceListAdapter.openLoadAnimation();
        attendanceListAdapter.isFirstOnly(false);
        attendanceListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvAttendanceHistory.setAdapter(attendanceListAdapter);
    }

}
