package com.attendancesystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.attendancesystem.R;
import com.attendancesystem.adapter.StudentUnitListAdapter;
import com.attendancesystem.adapter.UnitListAdapter;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.database.entity.Unit;
import com.attendancesystem.database.entity.UnitStudents;
import com.attendancesystem.utils.Utils;
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
 * Created by shraddha on 3/24/2018.
 */

public class AddStudentActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etRollNumber)
    EditText etRollNumber;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.rvSubjects)
    RecyclerView rvSubjects;
    private List<Unit> lsUnit;
    private List<UnitStudents> lsStudentUnits;
    private StudentUnitListAdapter studentUnitListAdapter;
    private Student studentBean;
    private boolean isEdit = false;

    @Override
    public int getContentView() {
        return R.layout.activity_add_student;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

    }

    @SuppressLint("CheckResult")
    @Override
    public void initViews() {
        tvTitle.setText("Add Student");


        if(getIntent().hasExtra("studentBean"))
        {
            studentBean = new Student();
            studentBean = getIntent().getParcelableExtra("studentBean");
            isEdit = true;
            tvTitle.setText("Edit Student");
            btnSubmit.setText("Update Student");
            etRollNumber.setEnabled(false);
            etRollNumber.setText(studentBean.getRollNumber());
            etFirstName.setText(studentBean.getFirstName());
            etLastName.setText(studentBean.getLastName());
        }

        rvSubjects.setHasFixedSize(true);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        initAdapter(lsUnit);

        btnSubmit.setOnClickListener(view -> {
            if (etRollNumber.getText() != null && etRollNumber.getText().toString().trim().length() > 0) {
                if (etFirstName.getText() != null && etFirstName.getText().toString().trim().length() > 0) {
                    if (etLastName.getText() != null && etLastName.getText().toString().trim().length() > 0) {
                        if(!isEdit) {
                            Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AddStudentActivity.this)
                                    .getStudentDao()
                                    .insert(new Student(etRollNumber.getText().toString(),
                                            etFirstName.getText().toString(),
                                            etLastName.getText().toString())))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.computation()).subscribe(() -> {
                                Log.e("DB", "inserted");
                                //finish();
                                submitUnitStudentsData();

                            }, throwable -> {
                                Toast.makeText(AddStudentActivity.this, "Roll number already exists", Toast.LENGTH_SHORT).show();
                                Log.e("Error", throwable.getMessage());
                            });
                        }else {
                            studentBean.setFirstName(etFirstName.getText().toString());
                            studentBean.setLastName(etLastName.getText().toString());
                            Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AddStudentActivity.this)
                                    .getStudentDao()
                                    .update(studentBean))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.computation()).subscribe(() -> {
                                Log.e("DB", "updated");
                                //finish();
                                submitUnitStudentsData();

                            }, throwable -> {
                                Toast.makeText(AddStudentActivity.this, "Roll number already exists", Toast.LENGTH_SHORT).show();
                                Log.e("Error", throwable.getMessage());
                            });
                        }
                    } else {
                        Toast.makeText(AddStudentActivity.this, "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddStudentActivity.this, "First Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddStudentActivity.this, "Roll Number cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnitList();

    }

    @SuppressLint("CheckResult")
    private void submitUnitStudentsData() {
        if(!isEdit) {
            List<UnitStudents> lsUnitStudents = new ArrayList<>();

            for (int i = 0; i < studentUnitListAdapter.getData().size(); i++) {
                UnitStudents unitStudents = new UnitStudents();
                unitStudents.setRollNumber(etRollNumber.getText().toString());
                unitStudents.setSubCode(studentUnitListAdapter.getData().get(i).getSubCode());
                unitStudents.setSubjectSelected(studentUnitListAdapter.getData().get(i).isSelected());
                lsUnitStudents.add(unitStudents);
            }

            Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AddStudentActivity.this)
                    .getUnitStudentDao()
                    .insert(lsUnitStudents))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation()).subscribe(() -> {
                Log.e("DB", "insertedUnitStudents");
                finish();
            }, throwable -> {
                Toast.makeText(AddStudentActivity.this, "Roll number already exists", Toast.LENGTH_SHORT).show();
                Log.e("Error", throwable.getMessage());
            });
        }else{
            for (int i = 0; i < studentUnitListAdapter.getData().size(); i++) {
                for (int j = 0; j < lsStudentUnits.size(); j++) {
                    if(studentUnitListAdapter.getData().get(i).getSubCode().matches(lsStudentUnits.get(j).getSubCode())){
                        if(studentUnitListAdapter.getData().get(i).isSelected()){
                            lsStudentUnits.get(j).setSubjectSelected(true);
                        }else{
                            lsStudentUnits.get(j).setSubjectSelected(false);
                        }
                    }
                }
            }
            Completable.fromRunnable(() -> DatabaseMain.getDbInstance(AddStudentActivity.this)
                    .getUnitStudentDao()
                    .update(lsStudentUnits))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation()).subscribe(() -> {
                Log.e("DB", "updatedUnitStudents");
                finish();
            }, throwable -> {
                Log.e("Error", throwable.getMessage());
            });
        }
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
                    //initAdapter(lsStudent);
                    setListData(lsUnit);
                });
    }

    @SuppressLint("CheckResult")
    private void getUnitsForStudent(){
        Observable.fromCallable(() -> DatabaseMain.getDbInstance(this).getUnitStudentDao().getUnitsOfStudent(studentBean.getRollNumber()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(units -> {
                    Log.e("EDIT", units.size() + "");
                    lsStudentUnits = new ArrayList<>();
                    lsStudentUnits.addAll(units);

                }, throwable -> {
                    Log.e("ERROR", throwable.getMessage());
                }, () -> {
                    //initAdapter(lsStudent);
                    studentUnitListAdapter.selectUnitsForUpdate(lsStudentUnits);
                });
    }

    private void initAdapter(List<Unit> lsUnit) {
        studentUnitListAdapter = new StudentUnitListAdapter(lsUnit);
        studentUnitListAdapter.openLoadAnimation();
        //studentUnitListAdapter.isFirstOnly(false);
        studentUnitListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvSubjects.setAdapter(studentUnitListAdapter);


    }

    private void setListData(List<Unit> lsUnit) {
        if (lsUnit != null && lsUnit.size() > 0) {
            studentUnitListAdapter.setNewData(lsUnit);
            if(isEdit){
                getUnitsForStudent();
            }
        }
    }

}
