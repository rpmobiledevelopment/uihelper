package com.rp.uihelpher.helpher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.rp.uihelpher.log.IsLog;

public class OnCall {

    private String TAG = OnCall.class.getSimpleName();

    public OnCall(Activity mActivity, String phoneNumber) {
        try {
            Intent dial = new Intent();
            dial.setAction("android.intent.action.DIAL");
            try {
                dial.setData(Uri.parse("tel:"+phoneNumber));
                mActivity.startActivity(dial);
            } catch (Exception e) {
                new IsLog("Calling", "" + e.getMessage());
            }
        }catch (NumberFormatException | NullPointerException e) {
            new IsLog(TAG,"NumberFormatException======"+e.getMessage());
        }catch (Exception e) {
            new IsLog(TAG,"Exception======"+e.getMessage());
        }
    }

}
