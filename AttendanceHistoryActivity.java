package com.attendancesystem.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.attendancesystem.R;
import com.attendancesystem.adapter.AttendanceHistoryListAdapter;
import com.attendancesystem.adapter.AttendanceListAdapter;
import com.attendancesystem.bean.AttendanceBean;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Attendance;
import com.attendancesystem.database.entity.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shraddha on 3/25/2018.
 */

public class AttendanceHistoryActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvAttendanceHistory)
    RecyclerView rvAttendanceHistory;

    private List<Attendance> lsAttendance;
    private AttendanceHistoryListAdapter attendanceListAdapter;
    private View notDataView;


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
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvAttendanceHistory.getParent(), false);
        notDataView.setOnClickListener(v -> getAttendanceHistory());
        initAdapter(lsAttendance);
    }

    private void initAdapter(List<Attendance> lsAttendance) {
        attendanceListAdapter = new AttendanceHistoryListAdapter(lsAttendance);
        attendanceListAdapter.openLoadAnimation();
        attendanceListAdapter.isFirstOnly(false);
        attendanceListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvAttendanceHistory.setAdapter(attendanceListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAttendanceHistory();
    }

    private void setListData(List<Attendance> lsAttendance){
        if(lsAttendance !=null && lsAttendance.size()>0)
            attendanceListAdapter.setNewData(lsAttendance);
        else
            attendanceListAdapter.setEmptyView(notDataView);
    }

    private void getAttendanceHistory(){
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getAttendanceDao().getAttendanceHistory())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attendances -> {
                    lsAttendance = new ArrayList<>();
                    lsAttendance.addAll(attendances);

                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {
                    //initAdapter(lsStudent);
                    setListData(lsAttendance);
                });

    }

}
