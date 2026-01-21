package com.ui.helper.constant

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class OnKeyboardHide {
    constructor(activity: Activity?, editText: EditText?) {
        if (activity != null) {
            val imm =
                checkNotNull(activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(editText?.windowToken, 0)
        }
    }

    constructor(activity: Activity?, view: View?) {
        if (activity != null) {
            val imm =
                checkNotNull(activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    constructor(activity: Activity?, view: View?, rv: View?) {
        rv?.visibility = View.GONE
        if (activity != null) {
            val imm =
                checkNotNull(activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    constructor(activity: Activity?, view: View?, visible: String?) {
        if (activity != null) {
            val imm =
                checkNotNull(activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    constructor(activity: Activity, view: View?, editText: EditText?) {
        editText?.requestFocus()
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    constructor(activity: Activity, view: View?, editText: View?, editText1: View?) {
        editText?.requestFocus()
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    constructor(activity: Activity?) {
        activity?.let {
            val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = it.currentFocus
            if (view == null) {
                view = View(it)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    constructor(view: EditText?, enable: String?) {
        if (enable == "TRUE") {
            view?.setFocusable(true)
            view?.isFocusableInTouchMode = true
            view?.setTextIsSelectable(true)
        } else {
            view?.setFocusable(false)
            view?.isFocusableInTouchMode = false
            view?.setTextIsSelectable(false)
        }
    }
}
