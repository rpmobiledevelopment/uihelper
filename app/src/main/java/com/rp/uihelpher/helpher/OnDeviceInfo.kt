package com.rp.uihelpher.helpher

import android.content.Context
import android.os.Build
import android.provider.Settings

class OnDeviceInfo {
    val name: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            if (model.startsWith(manufacturer)) {
                return capitalize(model)
            } else {
                return capitalize(manufacturer) + "-" + model
            }
        }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }

    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    }
}
