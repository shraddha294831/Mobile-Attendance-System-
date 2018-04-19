package com.attendancesystem.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.attendancesystem.database.entity.Unit;
import com.attendancesystem.database.entity.UnitStudents;

import java.util.List;

/**
 * Created by shraddha on 4/14/2018.
 */

@Dao
public interface UnitStudentsDao {

    @Insert
    void insert(UnitStudents... unitStudents);

    @Insert
    void insert(UnitStudents unitStudents);

    @Insert
    void insert(List<UnitStudents> unitStudents);

    @Update
    void update(List<UnitStudents> unitStudents);

    @Update
    void update(UnitStudents unitStudents);

    @Query("select * from UnitStudents where rollNumber=:rollNumber")
    List<UnitStudents> getUnitsOfStudent(String rollNumber);



}
