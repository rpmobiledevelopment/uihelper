package com.ui.helper.constant

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.ui.helper.log.IsLog

class OnCall(mActivity: Context?, phoneNumber: String?) {
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
