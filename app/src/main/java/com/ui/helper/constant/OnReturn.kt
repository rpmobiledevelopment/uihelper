package com.ui.helper.constant

import android.text.Html
import android.view.View
import android.widget.TextView

class OnReturn {

    // Return empty
    fun onTextReturned(msg: String?, textView: TextView?, opt: String) {
        if (msg != null && !msg.isEmpty()) {
            if (msg == "NON") {
                textView?.visibility = View.GONE
            } else {
                textView?.visibility = View.VISIBLE
                textView?.text = msg
            }
        } else {
            when (opt) {
                "NON", "RETURN_HIDE" -> textView?.visibility = View.GONE
                "RETURN_VALUE_NOT_EMPTY" -> textView?.text = "-"
                else -> textView?.text = ""
            }
        }
    }

    // Return empty
    fun onTextReturned(msg: Int, textView: TextView?, opt: String?) {
        try {
            if (msg > 0) {
                textView?.visibility = View.VISIBLE
                if (msg > 99) {
                    textView?.text = "99+"
                } else {
                    textView?.text = msg.toString() + ""
                }
            } else {
                textView?.text = ""
                textView?.visibility = View.GONE
            }
        } catch (_: NullPointerException) {
            textView?.text = ""
            textView?.visibility = View.GONE
        } catch (_: NumberFormatException) {
            textView?.text = ""
            textView?.visibility = View.GONE
        } catch (_: Exception) {
            textView?.text = ""
            textView?.visibility = View.GONE
        }
    }

    // Return empty html viewer
    fun onTextReturned(msg: String?, textView: TextView?, opt: String, htmlViewer: Boolean) {
        try {
            if (msg != null && !msg.isEmpty()) {
                if (msg == "NON") {
                    textView?.visibility = View.GONE
                } else {
                    textView?.visibility = View.VISIBLE
                    textView?.text = Html.fromHtml(msg)
                }
            } else {
                when (opt) {
                    "NON", "RETURN_HIDE" -> textView?.visibility = View.GONE
                    "RETURN_VALUE_NOT_EMPTY" -> textView?.text = "-"
                    else -> textView?.text = ""
                }
            }
        } catch (e: NullPointerException) {
            textView?.text = ""
            textView?.visibility = View.GONE
        } catch (e: NumberFormatException) {
            textView?.text = ""
            textView?.visibility = View.GONE
        } catch (e: Exception) {
            textView?.text = ""
            textView?.visibility = View.GONE
        }
    }

    fun text(msg: String?): String {
        return if (msg != null && !msg.isEmpty()) {
            msg
        } else {
            ""
        }
    }

}