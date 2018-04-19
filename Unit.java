package com.attendancesystem.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by srujan on 4/14/2018.
 */
@Entity
public class Unit implements Parcelable {

    @PrimaryKey @NonNull
    private String subCode;

    @NonNull
    private String collegeName;

    @NonNull
    private String title;

    @NonNull
    private String lecturer;

    @NonNull
    private String email;

    @Ignore
    private boolean isSelected;

    public Unit() {
    }

    public Unit(@NonNull String subCode, @NonNull String collegeName, @NonNull String title, @NonNull String lecturer, @NonNull String email) {
        this.subCode = subCode;
        this.collegeName = collegeName;
        this.title = title;
        this.lecturer = lecturer;
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @NonNull
    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(@NonNull String subCode) {
        this.subCode = subCode;
    }

    @NonNull
    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(@NonNull String collegeName) {
        this.collegeName = collegeName;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(@NonNull String lecturer) {
        this.lecturer = lecturer;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subCode);
        dest.writeString(this.collegeName);
        dest.writeString(this.title);
        dest.writeString(this.lecturer);
        dest.writeString(this.email);
    }

    protected Unit(Parcel in) {
        this.subCode = in.readString();
        this.collegeName = in.readString();
        this.title = in.readString();
        this.lecturer = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<Unit> CREATOR = new Parcelable.Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel source) {
            return new Unit(source);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
}
