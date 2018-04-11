package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.bean.StudentBean;
import com.attendancesystem.database.entity.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shraddha on 3/21/2018.
 */

public class StudentListAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {

    public StudentListAdapter(@Nullable List<Student> data) {
        super(R.layout.row_student_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        ((TextView)helper.getView(R.id.tvRollNumber)).setText(item.getRollNumber()+"");
        ((TextView)helper.getView(R.id.tvName)).setText(item.getFirstName() + " " + item.getLastName());
    }
}
