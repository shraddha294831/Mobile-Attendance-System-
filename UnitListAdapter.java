package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.database.entity.Unit;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by shraddha on 4/15/2018.
 */

public class UnitListAdapter extends BaseQuickAdapter<Unit, BaseViewHolder> {

    public UnitListAdapter(@Nullable List<Unit> data) {
        super(R.layout.row_unit_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Unit item) {
        ((TextView)helper.getView(R.id.tvSubjectTitle)).setText(item.getTitle());
        ((TextView)helper.getView(R.id.tvSubjectCode)).setText(item.getSubCode());
        ((TextView)helper.getView(R.id.tvLecturer)).setText(item.getLecturer());
        ((TextView)helper.getView(R.id.tvLecturerEmail)).setText(item.getEmail());
        ((TextView)helper.getView(R.id.tvCollegeName)).setText(item.getCollegeName());

    }
}
