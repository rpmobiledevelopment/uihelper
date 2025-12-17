package com.rp.uihelpher.localStorage

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.widget.TextView

class SharedPre {
    // Set TextView
    constructor()

    // Set TextView
    constructor(mActivity: Activity?, TAG_VALUE: String?, textView: TextView) {
        if (getDef(mActivity, TAG_VALUE) != null && (getDef(mActivity, TAG_VALUE) != "") && !getDef(
                mActivity,
                TAG_VALUE
            ).isEmpty()
        ) {
            textView.setText(getDef(mActivity, TAG_VALUE))
        } else {
            textView.setText("")
        }
    }

    // return Without Empty Text
    fun onReturnImg(mActivity: Activity?, TAG_VALUE: String?): String? {
        if (getDef(mActivity, TAG_VALUE) != null &&
            getDef(mActivity, TAG_VALUE).length > 5
        ) {
            return getDef(mActivity, TAG_VALUE)
        } else {
            return ""
        }
    }

    companion object {
        // return Text
        fun onReturnText(mActivity: Activity?, TAG_VALUE: String?): String? {
            if (getDef(mActivity, TAG_VALUE) != null && (getDef(
                    mActivity,
                    TAG_VALUE
                ) != "") && !getDef(mActivity, TAG_VALUE).isEmpty()
            ) {
                return getDef(mActivity, TAG_VALUE)
            } else {
                return ""
            }
        }

        // return Text
        fun onReturnText(mActivity: Context?, TAG_VALUE: String?): String? {
            if (getDef(mActivity, TAG_VALUE) != null && (getDef(
                    mActivity,
                    TAG_VALUE
                ) != "") && !getDef(mActivity, TAG_VALUE).isEmpty()
            ) {
                return getDef(mActivity, TAG_VALUE)
            } else {
                return ""
            }
        }

        // return Int
        fun onReturnInt(mActivity: Activity?, TAG_VALUE: String?): Int {
            if (getDef(mActivity, TAG_VALUE) != null && (getDef(
                    mActivity,
                    TAG_VALUE
                ) != "") && !getDef(mActivity, TAG_VALUE).isEmpty()
            ) {
                return getDef(mActivity, TAG_VALUE).toInt()
            } else {
                return 10000
            }
        }

        @JvmStatic
        fun setDef(context: Context?, key: String?, value: String?) {
            val preferences = context?.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.putString(key, value)
            editor?.apply()
        }

        fun getDef(context: Context?, key: String?): String {
            val preferences = context?.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            return preferences?.getString(key, "") ?: ""
        }

        fun getDefInt(context: Context?, key: String?): Int {
            val preferences = context?.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            return preferences?.getInt(key, 0) ?: 0
        }

        fun removeDef(preferenceName: String?, context: Context?) {
            val preferences = context?.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.remove(preferenceName)
            editor?.apply()
        }

        fun setDef(context: Context?, key: String?, value: Int) {
            val preferences = context?.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.putString(key, value.toString())
            editor?.apply()
        }
    }
}
