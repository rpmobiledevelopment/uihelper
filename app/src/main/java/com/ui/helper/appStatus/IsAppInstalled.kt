package com.ui.helper.appStatus

import android.content.Context
import android.content.pm.PackageManager

object IsAppInstalled {
    private val TAG: String = IsAppInstalled::class.java.simpleName

    fun isStatus(context: Context, packageName: String?): Boolean {
        val pm = context.packageManager
        try {
            packageName?.let {
                pm.getPackageInfo(it, PackageManager.GET_ACTIVITIES)
            }

            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }
}
