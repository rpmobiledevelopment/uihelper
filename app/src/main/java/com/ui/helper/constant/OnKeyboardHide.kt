package com.ui.helper.constant

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class OnKeyboardHide {

    constructor(editText: EditText?) {
        val imm = checkNotNull(editText?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    constructor(view: View?) {
        val imm = checkNotNull(view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    constructor(view: View?, rv: View?) {
        rv?.visibility = View.GONE
        val imm =
            checkNotNull(view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    constructor(view: View?, visible: String?) {
        val imm =
            checkNotNull(view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    constructor(view: View?, editText: EditText?) {
        editText?.requestFocus()
        val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    constructor(view: View?, editText: View?, editText1: View?) {
        editText?.requestFocus()
        val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(context: Context?) {

        context?.let {

            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val activity = it as? Activity
            var view = activity?.currentFocus

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
