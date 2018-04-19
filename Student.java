package com.attendancesystem.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by srujan on 3/20/2018.
 */

@Entity
public class Student implements Parcelable {

    @PrimaryKey @NonNull
    private String rollNumber;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    public Student() {
    }

    public Student(String rollNumber, @NonNull String firstName, @NonNull String lastName) {
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rollNumber);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    protected Student(Parcel in) {
        this.rollNumber = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
