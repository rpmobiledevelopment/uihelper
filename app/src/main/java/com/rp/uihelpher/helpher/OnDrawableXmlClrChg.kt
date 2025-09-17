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
import com.rp.uihelpher.log.IsLog

class OnDrawableXmlClrChg {

    var TAG: String = OnDrawableXmlClrChg::class.java.simpleName

    constructor(mActivity: Activity, mView: View, mColor: Int, opt: String, width: Int) {
        when (opt) {
            "BACKGROUND_XML_COLOR" -> {
                (mView.background.mutate() as? GradientDrawable)?.let { bg ->
                    val strokeColor = ContextCompat.getColor(mActivity, mColor)
                    bg.setStroke(width, strokeColor)
                }
            }
            "BACKGROUND_XML_COLOR_FULL" -> {
                (mView.background.mutate() as? GradientDrawable)?.let { bg ->
                    val strokeColor = ContextCompat.getColor(mActivity, mColor)
                    bg.setColor(strokeColor)          // changes <solid> fill
                    bg.setStroke(width, strokeColor)  // changes <stroke> width & color
                }
            }

            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
        }
    }
    constructor(mActivity: Activity, mView: View, mColor: Int, alpha: Int, opt: String) {
        when (opt) {
            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)

            "BACKGROUND_TEXT_DRAWABLE_XML_COLOR_ALPHA" -> onTextWithAlpha(mView as? TextView, mColor, alpha)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, alpha)
        }
    }

    constructor(mActivity: Context, mView: View, mColor: Int, opt: String) {
        try {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

                "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)


                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
            }
        } catch (e: Exception) {
            // fallback default (gray tint)

            IsLog(TAG,"==========================${e.message}")
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView)

                "BACKGROUND_XML_TEXT_COLOR","BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView)

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
            ContextCompat.getColor(mActivity, color ?: R.color.gray_color),
            PorterDuff.Mode.SRC_IN
        )
    }

    // ----------------------
    // TEXT COLOR CHANGE
    // ----------------------
    private fun onTextTint(mActivity: Context, textView: TextView?, color: Int? = null) {

        IsLog(TAG,"color=============$color")
        val drawable = textView?.background?.mutate()
        val finalColor = ContextCompat.getColor(mActivity, color ?: R.color.gray_color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable?.colorFilter = BlendModeColorFilter(finalColor, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            drawable?.setColorFilter(finalColor, PorterDuff.Mode.SRC_IN)
        }

    }
    private fun onTextTint(mActivity: Context, textView: TextView?, color: Int?,color1: Int?,) {

        val gradient = textView?.background?.mutate() as GradientDrawable
        gradient.colors = intArrayOf(
            ContextCompat.getColor(mActivity, color ?: R.color.gray_color), // start color
            ContextCompat.getColor(mActivity, color ?: R.color.gray_color)  // end color
        )

    }
    private fun onTextWithAlpha(textView: TextView?, color: Int?,opacity: Int?) {
        val colorWithAlpha = ColorUtils.setAlphaComponent(color!!, opacity!!)
        val background = textView?.background?.mutate() as GradientDrawable
        background.setColor(colorWithAlpha)
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

