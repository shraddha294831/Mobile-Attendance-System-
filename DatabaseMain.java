package com.attendancesystem.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.attendancesystem.database.dao.AttendanceDao;
import com.attendancesystem.database.dao.StudentDAO;
import com.attendancesystem.database.entity.Attendance;
import com.attendancesystem.database.entity.Student;
import com.attendancesystem.utils.DateConverter;


/**
 * Created by Shraddha on 3/20/2018.
 */

@Database(entities = {Student.class, Attendance.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseMain extends RoomDatabase{

    private static final String DB_NAME = "SAS.db";
    private static volatile DatabaseMain dbInstance;

    public static synchronized DatabaseMain getDbInstance(Context mContext){
        if(dbInstance == null){
            dbInstance = create(mContext);
        }
        return dbInstance;
    }

    private static DatabaseMain create(Context mContext) {
        return Room.databaseBuilder(mContext, DatabaseMain.class, DB_NAME).build();
    }

    public abstract StudentDAO getStudentDao();

    public abstract AttendanceDao getAttendanceDao();
}
