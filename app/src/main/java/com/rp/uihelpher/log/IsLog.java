package com.rp.uihelpher.log;

import android.util.Log;

import com.rp.uihelpher.BuildConfig;

public class IsLog {

    public IsLog(String className, String print) {

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.e(className,print);
        } else {
//            Log.e(className,print);
        }
    }

}
