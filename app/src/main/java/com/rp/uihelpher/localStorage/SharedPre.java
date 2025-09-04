package com.rp.uihelpher.localStorage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SharedPre {

    // Set TextView
    public SharedPre() {}

    // Set TextView
    public SharedPre(Activity mActivity, String TAG_VALUE, TextView textView) {
        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
                !SharedPre.getDef(mActivity,TAG_VALUE).equals("") &&
                !SharedPre.getDef(mActivity,TAG_VALUE).isEmpty()) {
            textView.setText(SharedPre.getDef(mActivity,TAG_VALUE));
        }else {
            textView.setText("");
        }
    }

//    // Set ImageView
//    public SharedPre(Activity mActivity, String TAG_VALUE, ImageView imageView) {
//        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
//                SharedPre.getDef(mActivity,TAG_VALUE).length()>5) {
//            Glide.with(mActivity).load(SharedPre.getDef(mActivity,TAG_VALUE))
//                    .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
//                    .into(imageView);
//        }else {
//            Glide.with(mActivity).load(R.drawable.ic_user)
//                    .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
//                    .into(imageView);
//        }
//    }

    // return Text
    public static String onReturnText(Activity mActivity,String TAG_VALUE) {
        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
                !SharedPre.getDef(mActivity,TAG_VALUE).equals("") &&
                !SharedPre.getDef(mActivity,TAG_VALUE).isEmpty()) {
            return SharedPre.getDef(mActivity,TAG_VALUE);
        }else {
            return "";
        }
    }
    // return Text
    public static String onReturnText(Context mActivity,String TAG_VALUE) {
        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
                !SharedPre.getDef(mActivity,TAG_VALUE).equals("") &&
                !SharedPre.getDef(mActivity,TAG_VALUE).isEmpty()) {
            return SharedPre.getDef(mActivity,TAG_VALUE);
        }else {
            return "";
        }
    }
    // return Int
    public static int onReturnInt(Activity mActivity,String TAG_VALUE) {
        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
                !SharedPre.getDef(mActivity,TAG_VALUE).equals("") &&
                !SharedPre.getDef(mActivity,TAG_VALUE).isEmpty()) {
            return Integer.parseInt(SharedPre.getDef(mActivity,TAG_VALUE));
        }else {
            return 10000;
        }
    }

    // return Without Empty Text
    public String onReturnImg(Activity mActivity,String TAG_VALUE) {
        if (SharedPre.getDef(mActivity,TAG_VALUE)!=null &&
                SharedPre.getDef(mActivity,TAG_VALUE).length()>5) {
            return SharedPre.getDef(mActivity,TAG_VALUE);
        }else {
            return "";
        }
    }

    public static void setDef(Context context, String key, String value) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDef(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static int getDefInt(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public static void removeDef(String preferenceName, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(preferenceName);
        editor.apply();
    }

    public static void setDef(Context context,String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();
    }

}
