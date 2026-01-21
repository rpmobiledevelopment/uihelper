package com.ui.helper.log;

import android.util.Log;

import com.ui.helper.BuildConfig;

public class IsLog {

    public IsLog(String className, String print) {

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.e(className,print);
        } else {
//            Log.e(className,print);
        }
    }

}
