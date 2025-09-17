package com.rp.uihelpher.helpher

import android.app.Activity
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.rp.uihelpher.R

class OnDrawableXmlClrChg {

    constructor(mActivity: Activity, mView: View, mColor: Int, opt: String, width: Int) {
        when (opt) {
            "BACKGROUND_XML_COLOR" -> {
                (mView.background as? GradientDrawable)?.let { bg ->
                    val strokeColor = ContextCompat.getColor(mActivity, mColor)
                    bg.setStroke(width, strokeColor)
                }
            }

            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
        }
    }

    constructor(mActivity: Context, mView: View, mColor: Int, opt: String) {
        try {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
            }
        } catch (_: Exception) {
            // fallback default (gray tint)
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView)
            }
        }
    }

    constructor(mActivity: Context, mView: View, mColor: Int) {
        try {
            onBgMutate(mActivity, mView, mColor)
        } catch (_: Exception) {
            onBgMutate(mActivity, mView)
        }
    }

    // ----------------------
    // IMAGE COLOR CHANGE
    // ----------------------
    private fun onImageTint(mActivity: Context, imageView: ImageView?, color: Int? = null) {
        imageView?.setColorFilter(
            color ?: ContextCompat.getColor(mActivity, R.color.gray_color),
            PorterDuff.Mode.SRC_IN
        )
    }

    // ----------------------
    // TEXT COLOR CHANGE
    // ----------------------
    private fun onTextTint(mActivity: Context, textView: TextView?, color: Int? = null) {
//        textView?.setTextColor(
//            color ?: ContextCompat.getColor(mActivity, R.color.gray_color)
//        )

        textView!!.background.mutate().setColorFilter(
            ContextCompat.getColor(
                mActivity,
                R.color.gray_color
            ), PorterDuff.Mode.SRC_IN
        )
    }

    // ----------------------
    // BACKGROUND COLOR CHANGE
    // ----------------------
    fun onBgMutate(mActivity: Context, mView: View) {
        val color = ContextCompat.getColor(mActivity, R.color.gray_color)
        mView.background?.mutate()?.let { drawable ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
            mView.background = drawable
        }
    }

    fun onBgMutate(mActivity: Context, mView: View, customColor: Int) {
        mView.background?.mutate()?.let { drawable ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(customColor, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                drawable.setColorFilter(customColor, PorterDuff.Mode.SRC_IN)
            }
            mView.background = drawable
        }
    }

    fun onBgMutate(mView: View, customColor: Int, opacity: Int) {
        val tintedColor = ColorUtils.setAlphaComponent(customColor, opacity)
        mView.background?.mutate()?.let { drawable ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(tintedColor, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                drawable.setColorFilter(tintedColor, PorterDuff.Mode.SRC_IN)
            }
            mView.background = drawable
        }
    }
}

