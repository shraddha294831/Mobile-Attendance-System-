package com.attendancesystem.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.attendancesystem.utils.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Shraddha on 3/21/2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "rollNumber",
        childColumns = "rollNumber", onDelete = CASCADE))
public class Attendance {

    @PrimaryKey(autoGenerate = true)
    private int attendanceId;

    @NonNull
    private String rollNumber;

    @NonNull
    private String facultyName;

    @NonNull
    private String studentName;

    @NonNull
    private boolean present;

    @NonNull
    private String subject;

    @NonNull
    @TypeConverters({DateConverter.class})
    private Date date;

    @NonNull
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(@NonNull String rollNumber) {
        this.rollNumber = rollNumber;
    }

    @NonNull
    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(@NonNull String facultyName) {
        this.facultyName = facultyName;
    }

    @NonNull
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(@NonNull String studentName) {
        this.studentName = studentName;
    }

    @NonNull
    public boolean isPresent() {
        return present;
    }

    public void setPresent(@NonNull boolean present) {
        this.present = present;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }
}
