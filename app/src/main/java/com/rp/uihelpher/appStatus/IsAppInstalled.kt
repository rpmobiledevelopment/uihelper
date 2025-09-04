package com.rp.uihelpher.appStatus

import android.content.Context
import android.content.pm.PackageManager

object IsAppInstalled {
    private val TAG: String = IsAppInstalled::class.java.simpleName

    fun isStatus(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }
}
