package com.attendancesystem.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.attendancesystem.R;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.database.entity.Unit;
import com.attendancesystem.utils.Utils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shraddha on 4/14/2018.
 */

public class UnitActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSubCode)
    EditText etSubCode;
    @BindView(R.id.etSubjectTitle)
    EditText etSubjectTitle;
    @BindView(R.id.etLecturerName)
    EditText etLecturerName;
    @BindView(R.id.etLecturerEmail)
    EditText etLecturerEmail;
    @BindView(R.id.etCollegeName)
    EditText etCollegeName;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvSubTitle)
    TextView tvSubTitle;

    private Unit unitBean;
    private boolean isEdit = false;

    @Override
    public int getContentView() {
        return R.layout.activity_units;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Subject");

        if(getIntent().hasExtra("unit"))
        {
            unitBean = getIntent().getParcelableExtra("unit");
            Log.e("Extra", unitBean.getSubCode());

            isEdit = true;

            etSubjectTitle.setText(unitBean.getTitle());
            etSubCode.setText(unitBean.getSubCode());
            etCollegeName.setText(unitBean.getCollegeName());
            etLecturerEmail.setText(unitBean.getEmail());
            etLecturerName.setText(unitBean.getLecturer());

            etSubCode.setEnabled(false);
            etSubCode.setFocusable(false);
            btnSubmit.setText("Update Subject");
            tvSubTitle.setText("Update Subject");
        }

        btnSubmit.setOnClickListener(view -> {

            if (etSubCode.getText() != null && etSubCode.getText().toString().trim().length() > 0) {
                if (etSubjectTitle.getText() != null && etSubjectTitle.getText().toString().trim().length() > 0) {
                    if (etLecturerName.getText() != null && etLecturerName.getText().toString().trim().length() > 0) {
                        if (etLecturerEmail.getText() != null && etLecturerEmail.getText().toString().trim().length() > 0) {
                            if (Utils.isValidEmail(etLecturerEmail.getText().toString())) {
                                if (etCollegeName.getText() != null && etCollegeName.getText().toString().trim().length() > 0) {
                                    Unit unit = new Unit();
                                    unit.setSubCode(etSubCode.getText().toString());
                                    unit.setTitle(etSubjectTitle.getText().toString());
                                    unit.setLecturer(etLecturerName.getText().toString());
                                    unit.setEmail(etLecturerEmail.getText().toString());
                                    unit.setCollegeName(etCollegeName.getText().toString());

                                    if(!isEdit) {
                                        Completable.fromRunnable(() -> DatabaseMain.getDbInstance(UnitActivity.this)
                                                .getUnitDao()
                                                .insert(unit))
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.computation()).subscribe(() -> {
                                            Log.e("DB", "inserted");
                                            finish();
                                        }, throwable -> {
                                            Toast.makeText(UnitActivity.this, "Subject code already exists", Toast.LENGTH_SHORT).show();
                                            Log.e("Error", throwable.getMessage());
                                        });
                                    }else {
                                        Completable.fromRunnable(() -> DatabaseMain.getDbInstance(UnitActivity.this)
                                                .getUnitDao()
                                                .update(unit))
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.computation()).subscribe(() -> {
                                            Log.e("DB", "updated");
                                            finish();
                                        }, throwable -> {
                                            Toast.makeText(UnitActivity.this, "Subject code already exists", Toast.LENGTH_SHORT).show();
                                            Log.e("Error", throwable.getMessage());
                                        });
                                    }
                                } else {
                                    Toast.makeText(UnitActivity.this, "College name cannot be empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UnitActivity.this, "Please enter valid Email address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UnitActivity.this, "Lecturer email cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UnitActivity.this, "Lecturer name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UnitActivity.this, "Subject title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UnitActivity.this, "Subject code cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
