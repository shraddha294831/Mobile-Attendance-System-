package com.attendancesystem.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.adapter.StudentListAdapter;
import com.attendancesystem.bean.StudentBean;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shraddha on 3/21/2018.
 */

public class StudentListActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvStudents)
    RecyclerView rvStudents;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    private List<Student> lsStudent;
    private StudentListAdapter studentListAdapter;
    private View notDataView;

    @Override
    public int getContentView() {
        return R.layout.activity_student_list;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Students");

        fabAdd.setOnClickListener(view -> startActivity(new Intent(StudentListActivity.this, AddStudentActivity.class)));

        rvStudents.setHasFixedSize(true);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvStudents.getParent(), false);
        notDataView.setOnClickListener(v -> getStudentList());

        initAdapter(lsStudent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentList();
    }

    private void getStudentList() {
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getStudentDao().getAllStudents())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(student -> {
                    Log.e("Name", student.size() + "");
                    lsStudent = new ArrayList<>();
                    lsStudent.addAll(student);
                    Log.e("LS", lsStudent.size() + "");

                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {
                    //initAdapter(lsStudent);
                    setListData(lsStudent);
                });
    }

    private void initAdapter(List<Student> lsStudent) {
        studentListAdapter = new StudentListAdapter(lsStudent);
        studentListAdapter.openLoadAnimation();
        studentListAdapter.isFirstOnly(false);
        studentListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvStudents.setAdapter(studentListAdapter);
    }

    private void setListData(List<Student> lsStudent){
        if(lsStudent !=null && lsStudent.size()>0)
            studentListAdapter.setNewData(lsStudent);
        else
            studentListAdapter.setEmptyView(notDataView);
    }
}
