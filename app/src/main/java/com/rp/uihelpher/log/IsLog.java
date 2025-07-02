package com.rp.uihelpher.log;

import android.util.Log;
public class IsLog {

    public IsLog(String className, String print) {
        Log.e(className,print);

//        if (BuildConfig.BUILD_TYPE.equals("debug")) {
//            Log.e(className,print);
//        } else {
////            Log.e(className,print+print);
//        }
    }

}
