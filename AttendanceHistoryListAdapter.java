package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.bean.AttendanceBean;
import com.attendancesystem.database.entity.Attendance;
import com.attendancesystem.utils.DateConverter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shraddha on 3/25/2018.
 */

public class AttendanceHistoryListAdapter extends BaseQuickAdapter<Attendance, BaseViewHolder> {

    public AttendanceHistoryListAdapter(@Nullable List<Attendance> data) {
        super(R.layout.row_attendance_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Attendance item) {
        ((TextView)helper.getView(R.id.tvDate)).setText(DateConverter.dateToTimestamp(item.getDate()));
        ((TextView)helper.getView(R.id.tvSubject)).setText(item.getSubCode());
    }
}