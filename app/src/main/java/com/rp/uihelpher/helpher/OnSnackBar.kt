package com.rp.uihelpher.helpher

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar
import com.rp.uihelpher.log.IsLog

class OnSnackBar {

    private val TAG: String = OnSnackBar::class.java.simpleName

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

    fun customSnackBar(mActivity: Activity, view: View, msg: String, showMsgLog: String) {
        try {
            val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)

            // Get Snackbar parent layout (it's a ViewGroup)
            val layout = snackBar.view as ViewGroup
            layout.setBackgroundColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.snack_bar_color))

            if (layout.childCount > 0) {
                layout.removeAllViews()
            }

            // Inflate your custom view
            val customView = LayoutInflater.from(mActivity).inflate(
                com.rp.uihelpher.R.layout.custom_snackbar, layout, false
            )

            val ll_custom = customView.findViewById<LinearLayout>(com.rp.uihelpher.R.id.ll_custom)
            val iv_msg_icon = customView.findViewById<ImageView>(com.rp.uihelpher.R.id.iv_msg_icon)
            val iv_close_icon = customView.findViewById<ImageView>(com.rp.uihelpher.R.id.iv_close_icon)
            // Set text
            val tvMessage = customView.findViewById<TextView>(com.rp.uihelpher.R.id.tv_message)

            if (showMsgLog == "WARNING") {
                OnDrawableXmlClrChg(mActivity,ll_custom,com.rp.uihelpher.R.color.warning_color,25,"BACKGROUND_XML_FULL_COLOR_ALPHA")
                OnDrawableXmlClrChg(mActivity,iv_msg_icon,com.rp.uihelpher.R.color.warning_color,25,"CHG_XML_IMAGE_COLOR")
                OnDrawableXmlClrChg(mActivity,iv_close_icon,com.rp.uihelpher.R.color.warning_color,25,"CHG_XML_IMAGE_COLOR")
                tvMessage.setTextColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.warning_color))
            }else if (showMsgLog == "INFO") {
                OnDrawableXmlClrChg(mActivity,ll_custom,com.rp.uihelpher.R.color.info_color,25,"BACKGROUND_XML_FULL_COLOR_ALPHA")
                OnDrawableXmlClrChg(mActivity,iv_msg_icon,com.rp.uihelpher.R.color.info_color,25,"CHG_XML_IMAGE_COLOR")
                OnDrawableXmlClrChg(mActivity,iv_close_icon,com.rp.uihelpher.R.color.info_color,25,"CHG_XML_IMAGE_COLOR")
                tvMessage.setTextColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.info_color))
            }else if (showMsgLog == "ERROR") {
                OnDrawableXmlClrChg(mActivity,ll_custom,com.rp.uihelpher.R.color.error_color,25,"BACKGROUND_XML_FULL_COLOR_ALPHA")
                OnDrawableXmlClrChg(mActivity,iv_msg_icon,com.rp.uihelpher.R.color.error_color,25,"CHG_XML_IMAGE_COLOR")
                OnDrawableXmlClrChg(mActivity,iv_close_icon,com.rp.uihelpher.R.color.error_color,25,"CHG_XML_IMAGE_COLOR")
                tvMessage.setTextColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.error_color))
            }else if (showMsgLog == "SUCCESS") {
                OnDrawableXmlClrChg(mActivity,ll_custom,com.rp.uihelpher.R.color.success_color,25,"BACKGROUND_XML_FULL_COLOR_ALPHA")
                OnDrawableXmlClrChg(mActivity,iv_msg_icon,com.rp.uihelpher.R.color.success_color,25,"CHG_XML_IMAGE_COLOR")
                OnDrawableXmlClrChg(mActivity,iv_close_icon,com.rp.uihelpher.R.color.success_color,25,"CHG_XML_IMAGE_COLOR")
                tvMessage.setTextColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.success_color))
            }else {
                OnDrawableXmlClrChg(mActivity,ll_custom,com.rp.uihelpher.R.color.info_color,25,"BACKGROUND_XML_FULL_COLOR_ALPHA")
                OnDrawableXmlClrChg(mActivity,iv_msg_icon,com.rp.uihelpher.R.color.info_color,25,"CHG_XML_IMAGE_COLOR")
                OnDrawableXmlClrChg(mActivity,iv_close_icon,com.rp.uihelpher.R.color.info_color,25,"CHG_XML_IMAGE_COLOR")
                tvMessage.setTextColor(ContextCompat.getColor(mActivity, com.rp.uihelpher.R.color.info_color))
            }
            tvMessage.text = msg
            layout.addView(customView)

            snackBar.show()
        } catch (e: Exception) {
            IsLog("SnackbarError", "Error: ${e.message}")
        }
    }
}
