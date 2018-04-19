package com.attendancesystem.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by shraddha on 4/14/2018.
 */

public class StudentAttendanceApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
