package com.attendancesystem.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by shraddha on 4/8/2018.
 */

public class Utils {

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
