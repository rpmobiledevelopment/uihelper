package com.ui.helper.log

import android.util.Log
import com.ui.helper.constant.GlobalData

class IsLog(className: String?, print: String?) {
    init {
        if (GlobalData.isDebugging) {
            Log.e(className, print.toString())
        }
    }
}
