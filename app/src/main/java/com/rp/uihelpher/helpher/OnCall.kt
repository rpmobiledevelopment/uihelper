package com.rp.uihelpher.helpher

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.rp.uihelpher.log.IsLog
import androidx.core.net.toUri

class OnCall(mActivity: Activity?, phoneNumber: String?) {
    private val TAG: String = OnCall::class.java.simpleName

    init {
        try {
            val dial = Intent()
            dial.action = "android.intent.action.DIAL"
            try {
                dial.data = ("tel:$phoneNumber").toUri()
                mActivity?.startActivity(dial)
            } catch (e: Exception) {
                IsLog("Calling", "" + e.message)
            }
        } catch (e: NumberFormatException) {
            IsLog(TAG, "NumberFormatException======" + e.message)
        } catch (e: NullPointerException) {
            IsLog(TAG, "NumberFormatException======" + e.message)
        } catch (e: Exception) {
            IsLog(TAG, "Exception======" + e.message)
        }
    }
}
