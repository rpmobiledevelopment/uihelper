package onActionText

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ui.helper.R
import onInteface.OnInterface

class OnActionText(fullContent: String, skipContent: String, tvMsg: TextView?,
                   private val onActionText: OnInterface.OnText,val errorCode:Int?) {
    init {
        val spannable = SpannableString(fullContent)
        val skipStart = fullContent.indexOf(skipContent)

        if (skipStart != -1) {
            val skipEnd = skipStart + skipContent.length

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onActionText.onAction("", "")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    tvMsg?.context?.let {
                        if (errorCode!=null && errorCode != 0) {
                            ds.color = ContextCompat.getColor(it, errorCode)
                        } else {
                            ds.color = ContextCompat.getColor(it, R.color.error_color)
                        }
                    }
                    ds.isUnderlineText = true
                }
            }

            // Apply clickable span
            spannable.setSpan(clickableSpan, skipStart, skipEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), skipStart, skipEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        tvMsg?.text = spannable
        tvMsg?.movementMethod = LinkMovementMethod.getInstance()
        tvMsg?.highlightColor = Color.TRANSPARENT
    }
}

class OnActionTextBold(fullContent: String, skipContent: String, tvMsg: TextView?,
                       private val onActionText: OnInterface.OnText,val errorCode:Int?) {
    init {
        val spannable = SpannableString(fullContent)
        val skipStart = fullContent.indexOf(skipContent)

        if (skipStart != -1) {
            val skipEnd = skipStart + skipContent.length

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onActionText.onAction("", "")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    tvMsg?.context?.let {
                        if (errorCode!=null && errorCode != 0) {
                            ds.color = ContextCompat.getColor(it, errorCode)
                        } else {
                            ds.color = ContextCompat.getColor(it, R.color.error_color)
                        }
                    }
                    ds.isUnderlineText = false
                }
            }

            // Apply clickable span
            spannable.setSpan(clickableSpan, skipStart, skipEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), skipStart, skipEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        tvMsg?.text = spannable
        tvMsg?.movementMethod = LinkMovementMethod.getInstance()
        tvMsg?.highlightColor = Color.TRANSPARENT
    }
}