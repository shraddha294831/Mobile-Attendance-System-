package com.attendancesystem.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.attendancesystem.database.entity.Unit;

import java.util.List;

/**
 * Created by shraddha on 4/14/2018.
 */

@Dao
public interface UnitDao {

    @Insert
    void insert(Unit... units);

    @Insert
    void insert(Unit unit);

    @Query("select * from Unit")
    List<Unit> getUnitList();

    @Update
    void update(Unit unit);
}
