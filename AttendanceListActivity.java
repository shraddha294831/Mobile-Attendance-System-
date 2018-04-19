package com.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attendancesystem.R;
import com.attendancesystem.adapter.AttendanceListAdapter;
import com.attendancesystem.adapter.StudentListAdapter;
import com.attendancesystem.bean.StudentBean;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Attendance;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.utils.DateConverter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by srujan on 3/24/2018.
 */

public class AttendanceListActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvStudents)
    RecyclerView rvStudents;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private AttendanceListAdapter attendanceListAdapter;
    private List<Student> lsStudent;
    private List<Attendance> lsAttendance;
    private View notDataView;
    String subject, faculty, attDate,subjectCode;

    private SweetAlertDialog sweetAlertDialog;

    @Override
    public int getContentView() {
        return R.layout.activity_attendance_list;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Attendance List");

        Bundle b = getIntent().getExtras();

        if (b != null) {
            subject = b.getString("subject");
            faculty = b.getString("faculty");
            attDate = b.getString("date");
            subjectCode = b.getString("subjectCode");
        }

        rvStudents.setHasFixedSize(true);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvStudents.getParent(), false);
        notDataView.setOnClickListener(v -> getStudentList());

        initAdapter(lsAttendance);

        btnSubmit.setVisibility(View.VISIBLE);
        btnSubmit.setOnClickListener(view -> {
            showSweetDialog();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentList();
    }

    private void initAdapter(List<Attendance> lsAttendance) {
        attendanceListAdapter = new AttendanceListAdapter(lsAttendance);
        attendanceListAdapter.openLoadAnimation();
        attendanceListAdapter.isFirstOnly(false);
        attendanceListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvStudents.setAdapter(attendanceListAdapter);
    }

    private void addAttendanceData() {
        Log.e("Student", "size " + lsStudent.size());

        lsAttendance = new ArrayList<>();
        for (int i = 0; i < lsStudent.size(); i++) {
            Attendance attendance = new Attendance();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = format.parse(attDate);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            attendance.setDate(date);
            attendance.setFacultyName(faculty);
            attendance.setSubCode(subjectCode);
            attendance.setRollNumber(lsStudent.get(i).getRollNumber());
            attendance.setStudentName(lsStudent.get(i).getFirstName() + " " + lsStudent.get(i).getLastName());
            lsAttendance.add(attendance);
        }

        setListData(lsAttendance);
        /*Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AttendanceListActivity.this).getAttendanceDao()
                .insert(lsAttendance)).subscribeOn(Schedulers.computation()).subscribe(() -> Log.e("Success", "success"),
                throwable -> Log.e("ERROR", throwable.getMessage()));*/
    }


    private void getStudentList() {
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getStudentDao().getStudentsForSubject(subjectCode))
                .subscribeOn(Schedulers.computation())
                .subscribe(student -> {
                    Log.e("Name", student.size() + "");
                    lsStudent = new ArrayList<>();
                    lsStudent.addAll(student);
                    Log.e("LS", lsStudent.size() + "");

                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {
                    //setListData(lsStudent);
                    addAttendanceData();
                });
    }

    private void setListData(List<Attendance> lsAttendance) {
        if (lsAttendance != null && lsAttendance.size() > 0)
            attendanceListAdapter.setNewData(lsAttendance);
        else
            attendanceListAdapter.setEmptyView(notDataView);
    }

    private void showSweetDialog() {
        sweetAlertDialog = new SweetAlertDialog(AttendanceListActivity.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Submit Attendance?")
                .setContentText("Present: " + attendanceListAdapter.getPresentCount() + " Absent:" + (lsAttendance.size() - attendanceListAdapter.getPresentCount()))
                .showCancelButton(true)
                .setConfirmText("Submit")
                .setCancelText("Cancel")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.cancel();
                })
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.setTitleText("Success!");
                    sweetAlertDialog.setContentText("Submitting attendance..").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                    Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AttendanceListActivity.this).getAttendanceDao()
                            .insert(attendanceListAdapter.getData())).subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                        sweetAlertDialog.setCancelable(false);
                                        sweetAlertDialog.setContentText("Attendance submitted successfully!")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(sweetAlertDialog1 -> {
                                                    sweetAlertDialog.cancel();
                                                    Intent i = new Intent(AttendanceListActivity.this, DashboardActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(i);

                                                }).showCancelButton(false)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    },
                                    throwable -> {
                                        sweetAlertDialog.cancel();
                                        Toast.makeText(AttendanceListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("ERROR", throwable.getMessage());
                                    });
                })
                .show();
    }

}
