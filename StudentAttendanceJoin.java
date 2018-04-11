package com.attendancesystem.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

//https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a

@Entity(tableName = "student_attendance_join", primaryKeys = {"rollNumber", "att_id"},
        foreignKeys = {@ForeignKey(entity = Student.class, parentColumns = "rollNumber", childColumns = "rollNumber"),
                @ForeignKey(entity = Attendance.class, parentColumns = "attendanceId", childColumns = "attendanceId")})
public class StudentAttendanceJoin {
    public final int attendanceId;
    public final int rollNumber;

    public StudentAttendanceJoin(int attendanceId, int rollNumber) {
        this.attendanceId = attendanceId;
        this.rollNumber = rollNumber;
    }
}
