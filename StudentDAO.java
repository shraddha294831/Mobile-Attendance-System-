package com.attendancesystem.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.attendancesystem.database.entity.Student;

import java.util.List;

/**
 * Created by srujan on 3/20/2018.
 */

@Dao
public interface StudentDAO {

    @Query("Select * from Student")
    List<Student> getAllStudents();

    @Insert
    void insert(Student... student);

    @Update
    void update(Student... student);

    @Delete
    void delete(Student... students);

    @Query("Select * from student where rollNumber=:rollNumber")
    Student getStudent(int rollNumber);

    @Query("Select * from student where rollNumber IN(select rollNumber from unitstudents where subCode=:subCode and isSubjectSelected='1')")
    List<Student> getStudentsForSubject(String subCode);

}
