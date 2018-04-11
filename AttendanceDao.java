package com.attendancesystem.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.attendancesystem.database.entity.Attendance;

import java.util.List;

/**
 * Created by Shraddha on 3/21/2018.
 */

@Dao
public interface AttendanceDao {


    @Insert
    void insert(List<Attendance> attendances);

    @Insert
    void insert(Attendance attendance);

    @Query("select * from Attendance where date=Date(:date)")
    List<Attendance> getAttendance(String date);
}
