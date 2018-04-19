package com.attendancesystem.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.attendancesystem.database.entity.Attendance;

import java.util.List;

/**
 * Created by shraddha on 3/21/2018.
 */

@Dao
public interface AttendanceDao {

    @Insert
    void insert(List<Attendance> attendances);

    @Insert
    void insert(Attendance attendance);

    @Query("select * from Attendance where date=:date and subCode=:subCode")
    List<Attendance> getAttendance(String date, String subCode);

    @Query("select * from attendance group by date,subcode order by date desc")
    List<Attendance> getAttendanceHistory();
}
