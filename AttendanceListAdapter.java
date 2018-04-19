package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.bean.StudentBean;
import com.attendancesystem.database.entity.Attendance;
import com.attendancesystem.database.entity.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srujan on 3/24/2018.
 */

public class AttendanceListAdapter extends BaseQuickAdapter<Attendance, BaseViewHolder> {

    public AttendanceListAdapter(@Nullable List<Attendance> data) {
        super(R.layout.row_attendance_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Attendance item) {
        ((TextView)helper.getView(R.id.tvRollNumber)).setText(item.getRollNumber()+"");
        ((TextView)helper.getView(R.id.tvName)).setText(item.getStudentName());

        CheckBox cbPresent = (CheckBox) helper.getView(R.id.cbPresent);

        if(item.isPresent()){
            cbPresent.setChecked(true);
        }else{
            cbPresent.setChecked(false);
        }

        cbPresent.setOnCheckedChangeListener((compoundButton, b) -> {
            getData().get(helper.getAdapterPosition()).setPresent(b);
        });
    }

    public int getPresentCount(){
        int present = 0;
        for (int i = 0; i < getData().size(); i++) {
            if(getData().get(i).isPresent())
                present = present+1;
        }
        return present;
    }
}
