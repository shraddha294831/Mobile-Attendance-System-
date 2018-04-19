package com.attendancesystem.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.attendancesystem.R;
import com.attendancesystem.database.entity.Unit;
import com.attendancesystem.database.entity.UnitStudents;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by srujan on 4/15/2018.
 */

public class StudentUnitListAdapter extends BaseQuickAdapter<Unit, BaseViewHolder> {

    public StudentUnitListAdapter(@Nullable List<Unit> data) {
        super(R.layout.row_subjects_students, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Unit item) {
        CheckBox cbSubject = (CheckBox) helper.getView(R.id.cbSubject);

        cbSubject.setText(item.getTitle());

        cbSubject.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                getData().get(helper.getAdapterPosition()).setSelected(true);
            } else {
                getData().get(helper.getAdapterPosition()).setSelected(false);
            }

            getSelectionCount();
        });

        if(item.isSelected()){
            cbSubject.setChecked(true);
        }else {
            cbSubject.setChecked(false);
        }
    }

    public int getSelectionCount() {
        int count = 0;
        for (int i = 0; i < getData().size(); i++) {
            if(getData().get(i).isSelected()){
                count = count +1;
            }
        }
        Log.e("Count", count+"");
        return count;
    }

    public void selectUnitsForUpdate(List<UnitStudents> lsUnitStudents){
        if(lsUnitStudents != null && lsUnitStudents.size()>0 && this.getData() != null && this.getData().size() >0){
            for (int i = 0; i < lsUnitStudents.size(); i++) {
                for(int j = 0; j < this.getData().size() ; j++){
                    if(lsUnitStudents.get(i).getSubCode().equalsIgnoreCase(this.getData().get(j).getSubCode())){
                        this.getData().get(i).setSelected(lsUnitStudents.get(i).isSubjectSelected());
                    }
                }
            }
            this.notifyDataSetChanged();
        }
    }
}
