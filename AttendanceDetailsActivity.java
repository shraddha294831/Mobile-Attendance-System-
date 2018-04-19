package com.attendancesystem.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.attendancesystem.R;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Unit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by srujan on 3/24/2018.
 */

public class AttendanceDetailsActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spSubject)
    Spinner spSubject;
    @BindView(R.id.etFacultyName)
    EditText etFacultyName;
    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Calendar myCalendar;
    ArrayList<Unit> lsUnit;
    ArrayAdapter<String> spnrSubAdapter;
    ArrayList<String> arrSub;

    @Override
    public int getContentView() {
        return R.layout.activity_attendance_details;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Attendance Details");

        myCalendar = Calendar.getInstance();

        getUnitList();

        btnSubmit.setOnClickListener(view -> {
            if (etFacultyName.getText() != null && etFacultyName.getText().toString().trim().length() > 0) {
                if (etDate.getText() != null && etDate.getText().toString().trim().length() > 0) {
                    getAttendanceList();
                } else {
                    Toast.makeText(AttendanceDetailsActivity.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AttendanceDetailsActivity.this, "Faculty Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                etFacultyName.setText(lsUnit.get(position).getLecturer());
                etFacultyName.setEnabled(false);
                etFacultyName.setFocusable(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AttendanceDetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint("CheckResult")
    private void getUnitList() {
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getUnitDao().getUnitList())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(units -> {
                    Log.e("Name", units.size() + "");
                    lsUnit = new ArrayList<>();
                    lsUnit.addAll(units);

                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {
                    arrSub = new ArrayList<>();
                    for (int i = 0; i < lsUnit.size(); i++) {
                        arrSub.add(lsUnit.get(i).getTitle());
                    }
                    spnrSubAdapter = new ArrayAdapter<String>(AttendanceDetailsActivity.this, R.layout.support_simple_spinner_dropdown_item, arrSub);
                    spSubject.setAdapter(spnrSubAdapter);
                });
    }

    @SuppressLint("CheckResult")
    private void getAttendanceList() {
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getAttendanceDao().getAttendance(etDate.getText().toString(), lsUnit.get(spSubject.getSelectedItemPosition()).getSubCode()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attendances -> {
                    Log.e("Attendance", attendances.size() + "");
                    if (attendances.size() > 0) {
                        Toast.makeText(AttendanceDetailsActivity.this, "Attendance for selected subject already exists on this date", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(AttendanceDetailsActivity.this, AttendanceListActivity.class);
                        i.putExtra("subject", spSubject.getSelectedItem().toString());
                        i.putExtra("subjectCode", lsUnit.get(spSubject.getSelectedItemPosition()).getSubCode());
                        i.putExtra("faculty", etFacultyName.getText().toString());
                        i.putExtra("date", etDate.getText().toString());
                        startActivity(i);
                    }
                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {

                });
    }

}
