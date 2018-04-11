package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.bean.AttendanceBean;
import com.attendancesystem.bean.StudentBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by shraddha on 3/25/2018.
 */

public class AttendanceHistoryListAdapter extends BaseQuickAdapter<AttendanceBean, BaseViewHolder> {

    public AttendanceHistoryListAdapter(@Nullable ArrayList<AttendanceBean> data) {
        super(R.layout.row_attendance_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttendanceBean item) {
        ((TextView)helper.getView(R.id.tvDate)).setText(item.getDate()+"");
        ((TextView)helper.getView(R.id.tvSubject)).setText(item.getSubject());
    }
}