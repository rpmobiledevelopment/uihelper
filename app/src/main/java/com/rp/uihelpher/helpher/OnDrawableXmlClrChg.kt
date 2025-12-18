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
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.rp.uihelpher.R
import com.rp.uihelpher.log.IsLog

class OnDrawableXmlClrChg {

    var TAG: String = OnDrawableXmlClrChg::class.java.simpleName

    constructor(mActivity: Activity?, mView: View?, mColor: Int, opt: String, width: Int) {
        when (opt) {
            "BACKGROUND_XML_COLOR" -> {
                (mView?.background?.mutate() as? GradientDrawable)?.let { bg ->
                    mActivity?.let {
                        val strokeColor = ContextCompat.getColor(it, mColor)
                        bg.setStroke(width, strokeColor)
                    }
                }
            }
            "BACKGROUND_XML_COLOR_FULL" -> {
                (mView?.background?.mutate() as? GradientDrawable)?.let { bg ->
                    mActivity?.let {
                        val strokeColor = ContextCompat.getColor(it, mColor)
                        bg.setColor(strokeColor)          // changes <solid> fill
                        bg.setStroke(width, strokeColor)  // changes <stroke> width & color
                    }

                }
            }

            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
        }
    }
    constructor(mActivity: Activity?, mView: View?, mColor: Int?,borderColor: Int?, opt: String?, width: Int?) {
        when (opt) {
            "BACKGROUND_XML_COLOR" -> {
                (mView?.background?.mutate() as? GradientDrawable)?.let { bg ->
                    mActivity?.let { activity ->
                        if (mColor != null && width != null) {
                            bg.setStroke(width, ContextCompat.getColor(activity, mColor))
                        }
                    }
                }
            }
            "BACKGROUND_XML_COLOR_FULL_TWO_COLOR" -> {
                (mView?.background?.mutate() as? GradientDrawable)?.let { bg ->

                    mActivity?.let { activity ->
                        if (mColor!=null && borderColor!=null && width!=null) {
                            val strokeColor = ContextCompat.getColor(activity, mColor)
                            val strokeBorderColor = ContextCompat.getColor(activity, borderColor)
                            bg.setColor(strokeColor)          // changes <solid> fill
                            bg.setStroke(width, strokeBorderColor)  // changes <stroke> width & color
                        }
                    }

                }
            }
            "BACKGROUND_XML_COLOR_FULL" -> {
                (mView?.background?.mutate() as? GradientDrawable)?.let { bg ->
                    mActivity?.let { activity ->
                        if (mColor != null && width != null) {
                            val strokeColor = ContextCompat.getColor(activity, mColor)
                            bg.setColor(strokeColor)          // changes <solid> fill
                            bg.setStroke(width, strokeColor)  // changes <stroke> width & color
                        }
                    }

                }
            }

            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
        }
    }
    constructor(mActivity: Activity?, mView: View?, mColor: Int?, alpha: Int?, opt: String?) {
        when (opt) {
            "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

            "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

            "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)

            "BACKGROUND_TEXT_DRAWABLE_XML_COLOR_ALPHA" -> onTextWithAlpha(mView as? TextView, mColor, alpha)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, alpha)
        }
    }

    constructor(mActivity: Context?, mView: View?, mColor: Int?, opt: String?) {
        try {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView, mColor)

                "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView, mColor, mColor)


                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView, mColor, 25)
            }
        } catch (e: Exception) {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(mActivity, mView as? ImageView)

                "BACKGROUND_XML_TEXT_COLOR","BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(mActivity, mView as? TextView)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity, mView)
            }
        }
    }

    constructor(mActivity: Context?, mView: View?, mColor: Int?) {
        try {
            onBgMutate(mActivity, mView, mColor)
        } catch (_: Exception) {
            onBgMutate(mActivity, mView)
        }
    }

    private fun onImageTint(mActivity: Context?, imageView: ImageView?, color: Int? = null) {
        mActivity?.let {
            imageView?.setColorFilter(
                ContextCompat.getColor(it, color ?: R.color.gray_color),
                PorterDuff.Mode.SRC_IN
            )
        }

    }

    // ----------------------
    // TEXT COLOR CHANGE
    // ----------------------
    private fun onTextTint(mActivity: Context?, textView: TextView?, color: Int? = null) {

//        IsLog(TAG,"color=============$color")
        val drawable = textView?.background?.mutate()

        mActivity?.let {
            val finalColor = ContextCompat.getColor(it, color ?: R.color.gray_color)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable?.colorFilter = BlendModeColorFilter(finalColor, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                drawable?.setColorFilter(finalColor, PorterDuff.Mode.SRC_IN)
            }
        }


    }
    private fun onTextTint(mActivity: Context?, textView: TextView?, color: Int?,color1: Int?,) {

        mActivity?.let {
            val gradient = textView?.background?.mutate() as GradientDrawable
            gradient.colors = intArrayOf(
                ContextCompat.getColor(it, color ?: R.color.gray_color), // start color
                ContextCompat.getColor(it, color ?: R.color.gray_color)  // end color
            )
        }


    }
    private fun onTextWithAlpha(textView: TextView?, color: Int?,opacity: Int?) {
        val colorWithAlpha = ColorUtils.setAlphaComponent(color!!, opacity!!)
        val background = textView?.background?.mutate() as GradientDrawable
        background.setColor(colorWithAlpha)
    }

    fun onBgMutate(mActivity: Context?, mView: View?) {
        mActivity?.let {
            val color = ContextCompat.getColor(it, R.color.gray_color)
            mView?.background?.mutate()?.let { drawable ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                mView?.background = drawable
            }
        }
    }

    fun onBgMutate(mActivity: Context?, mView: View?, @ColorRes customColor: Int?) {
        mActivity?.let {
            if (customColor!=null) {
                val color = ContextCompat.getColor(mActivity, customColor) // 🔹 resolve resource to real color
                mView?.background?.mutate()?.let { drawable ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
                    } else {
                        @Suppress("DEPRECATION")
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                    }
                    mView.background = drawable
                }
            }
        }

    }

    fun onBgMutate(mView: View?, customColor: Int?, opacity: Int?) {
        if (customColor!=null && opacity!=null) {
            val tintedColor = ColorUtils.setAlphaComponent(customColor, opacity)
            mView?.background?.mutate()?.let { drawable ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawable.colorFilter = BlendModeColorFilter(tintedColor, BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    drawable.setColorFilter(tintedColor, PorterDuff.Mode.SRC_IN)
                }
                mView?.background = drawable
            }
        }

    }
}

