package com.rp.uihelpher.helpher

import android.text.Html
import android.view.View
import android.widget.TextView

class OnReturn {

    // Return empty to ifene
    fun onTextReturned(msg: String?, textView: TextView, opt: String) {
        if (msg != null && !msg.isEmpty()) {
            if (msg == "NON") {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
                textView.text = msg
            }
        } else {
            when (opt) {
                "NON", "RETURN_HIDE" -> textView.visibility = View.GONE
                "RETURN_VALUE_NOT_EMPTY" -> textView.text = "-"
                else -> textView.text = ""
            }
        }
    }

    // Return empty
    fun onTextReturned(msg: Int, textView: TextView, opt: String?) {
        try {
            if (msg > 0) {
                textView.setVisibility(View.VISIBLE)
                if (msg > 99) {
                    textView.setText("99+")
                } else {
                    textView.setText(msg.toString() + "")
                }
            } else {
                textView.setText("")
                textView.setVisibility(View.GONE)
            }
        } catch (e: NullPointerException) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        } catch (e: NumberFormatException) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        } catch (e: Exception) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        }
    }

    // Return empty html viewer
    fun onTextReturned(msg: String?, textView: TextView, opt: String, htmlViewer: Boolean) {
        try {
            if (msg != null && !msg.isEmpty()) {
                if (msg == "NON") {
                    textView.setVisibility(View.GONE)
                } else {
                    textView.setVisibility(View.VISIBLE)
                    textView.setText(Html.fromHtml(msg))
                }
            } else {
                when (opt) {
                    "NON", "RETURN_HIDE" -> textView.setVisibility(View.GONE)
                    "RETURN_VALUE_NOT_EMPTY" -> textView.setText("-")
                    else -> textView.setText("")
                }
            }
        } catch (e: NullPointerException) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        } catch (e: NumberFormatException) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        } catch (e: Exception) {
            textView.setText("")
            textView.setVisibility(View.GONE)
        }
    }

    fun text(msg: String?): String {
        if (msg != null && !msg.isEmpty()) {
            return msg
        } else {
            return ""
        }
    }

}