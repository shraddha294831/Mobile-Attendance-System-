package com.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.attendancesystem.adapter.UnitListAdapter;
import com.attendancesystem.database.DatabaseMain;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.database.entity.Unit;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UnitListActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvUnits)
    RecyclerView rvUnits;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    private List<Unit> lsUnit;
    private UnitListAdapter unitListAdapter;
    private View notDataView;

    @Override
    public int getContentView() {
        return R.layout.activity_unit_list;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("Subject List");

        fabAdd.setOnClickListener(view -> startActivity(new Intent(UnitListActivity.this, UnitActivity.class)));

        rvUnits.setHasFixedSize(true);
        rvUnits.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvUnits.getParent(), false);
        notDataView.setOnClickListener(v -> getUnitList());

        initAdapter(lsUnit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnitList();
    }

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

    private void initAdapter(List<Unit> lsUnit) {
        unitListAdapter = new UnitListAdapter(lsUnit);
        unitListAdapter.openLoadAnimation();
        unitListAdapter.isFirstOnly(false);
        unitListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvUnits.setAdapter(unitListAdapter);

        unitListAdapter.setOnItemClickListener((adapter, view, position) -> {
            unitListAdapter.getData().get(position);
            Intent i = new Intent(UnitListActivity.this, UnitActivity.class);
            i.putExtra("unit", unitListAdapter.getData().get(position));
            startActivity(i);
        });
    }

    private void setListData(List<Unit> lsUnit){
        if(lsUnit !=null && lsUnit.size()>0)
            unitListAdapter.setNewData(lsUnit);
        else
            unitListAdapter.setEmptyView(notDataView);
    }
}
