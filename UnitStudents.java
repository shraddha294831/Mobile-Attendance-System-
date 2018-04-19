package com.attendancesystem.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by shraddha on 4/14/2018.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Student.class, parentColumns = "rollNumber", childColumns = "rollNumber", onDelete = CASCADE),
        @ForeignKey(entity = Unit.class, parentColumns = "subCode", childColumns = "subCode", onDelete = CASCADE)})
public class UnitStudents {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String rollNumber;

    @NonNull
    private String subCode;

    @NonNull
    private boolean isSubjectSelected;

    @NonNull
    public boolean isSubjectSelected() {
        return isSubjectSelected;
    }

    public void setSubjectSelected(@NonNull boolean subjectSelected) {
        isSubjectSelected = subjectSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(@NonNull String rollNumber) {
        this.rollNumber = rollNumber;
    }

    @NonNull
    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(@NonNull String subCode) {
        this.subCode = subCode;
    }
}
