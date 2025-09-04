package com.rp.uihelpher.helpher

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar
import com.rp.uihelpher.log.IsLog

class OnSnackBar {

    private val TAG: String = OnSnackBar::class.java.getSimpleName()

    constructor(view: View, msg: String?) {
        val snackBar = Snackbar.make(view, "" + msg, Snackbar.LENGTH_LONG)
            .setAction("") { view1: View? -> }
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.getView()
        snackBar.show()
    }

    constructor(view: View, displayString: String?, duration: Int) {
        val snackbar = Snackbar
            .make(view, "" + displayString, Snackbar.LENGTH_LONG)
            .setAction("", View.OnClickListener { view1: View? -> })
        snackbar.setActionTextColor(Color.WHITE)
        val sbView = snackbar.getView()
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.GREEN)
        snackbar.duration = duration
        snackbar.show()
    }

    constructor(view: View, displayString: String?, action: String?, duration: Int) {
        val snackbar = Snackbar
            .make(view, "" + displayString, Snackbar.LENGTH_LONG)
            .setAction("" + action) { view1: View? -> }
        snackbar.setActionTextColor(Color.WHITE)
        val sbView = snackbar.getView()
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.GREEN)
        snackbar.duration = duration
        snackbar.show()
    }

    constructor(mActivity: Activity, view: View, msg: String) {
        try {
            val snackBar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("") { view1: View? -> }
            snackBar.setActionTextColor(Color.GREEN)
            snackBar.setBackgroundTint(
                ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.snack_bar_color))
            val sbView = snackBar.getView()
            val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
            textView.setBackgroundColor(
                ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.snack_bar_color)
            )
            textView.setTextColor(Color.BLACK)
            snackBar.show()
        } catch (e: NullPointerException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: IllegalArgumentException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: IllegalStateException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: Exception) {
            IsLog(TAG, "Exception " + e.message)
        }
    }
    constructor(mActivity: Activity, view: View, msg: String, mColour: Int) {
        try {
            val snackBar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("") { view1: View? -> }
            snackBar.setActionTextColor(Color.GREEN)
            snackBar.setBackgroundTint(
                ContextCompat.getColor(mActivity, mColour))
            val sbView = snackBar.getView()
            val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
            textView.setBackgroundColor(
                ContextCompat.getColor(mActivity, mColour)
            )
            textView.setTextColor(Color.BLACK)
            snackBar.show()
        } catch (e: NullPointerException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: IllegalArgumentException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: IllegalStateException) {
            IsLog(TAG, "NullPointerException " + e.message)
        } catch (e: Exception) {
            IsLog(TAG, "Exception " + e.message)
        }
    }
}
