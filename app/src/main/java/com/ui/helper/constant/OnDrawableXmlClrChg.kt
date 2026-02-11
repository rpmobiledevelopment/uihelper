package com.ui.helper.constant

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
import com.ui.helper.R

class OnDrawableXmlClrChg {

    var TAG: String = OnDrawableXmlClrChg::class.java.simpleName

    constructor(mView: View?, mColor: Int, opt: String, width: Int) {
        mView?.let {
            when (opt) {
                "BACKGROUND_XML_COLOR" -> {
                    (it.background?.mutate() as? GradientDrawable)?.let { bg ->
                        val strokeColor = ContextCompat.getColor(it.context, mColor)
                        bg.setStroke(width, strokeColor)
                    }
                }
                "BACKGROUND_XML_COLOR_FULL" -> {
                    (it.background?.mutate() as? GradientDrawable)?.let { bg ->
                        val strokeColor = ContextCompat.getColor(it.context, mColor)
                        bg.setColor(strokeColor)          // changes <solid> fill
                        bg.setStroke(width, strokeColor)  // changes <stroke> width & color

                    }
                }

                "CHG_XML_IMAGE_COLOR" -> onImageTint(it as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(it as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(it, mColor)

                "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(it as? TextView, mColor, mColor)

                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(it, mColor, 25)
            }
        }
    }

    constructor(mView: View?, mColor: Int?,borderColor: Int?, opt: String?, width: Int?) {
        mView?.let {
            when (opt) {
                "BACKGROUND_XML_COLOR" -> {
                    (it.background?.mutate() as? GradientDrawable)?.let { bg ->
                        if (mColor != null && width != null) {
                            bg.setStroke(width, ContextCompat.getColor(it.context, mColor))
                        }
                    }
                }
                "BACKGROUND_XML_COLOR_FULL_TWO_COLOR" -> {
                    (it.background?.mutate() as? GradientDrawable)?.let { bg ->
                        if (mColor!=null && borderColor!=null && width!=null) {
                            val strokeColor = ContextCompat.getColor(it.context, mColor)
                            val strokeBorderColor = ContextCompat.getColor(it.context, borderColor)
                            bg.setColor(strokeColor)          // changes <solid> fill
                            bg.setStroke(width, strokeBorderColor)  // changes <stroke> width & color
                        }
                    }
                }
                "BACKGROUND_XML_COLOR_FULL" -> {
                    (it.background?.mutate() as? GradientDrawable)?.let { bg ->
                        if (mColor != null && width != null) {
                            val strokeColor = ContextCompat.getColor(it.context, mColor)
                            bg.setColor(strokeColor)          // changes <solid> fill
                            bg.setStroke(width, strokeColor)  // changes <stroke> width & color
                        }
                    }
                }

                "CHG_XML_IMAGE_COLOR" -> onImageTint(it as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(it as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(it, mColor)

                "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(it as? TextView, mColor, mColor)

                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(it, mColor, 25)
            }
        }

    }

    constructor(mView: View?, mColor: Int?, alpha: Int?, opt: String?) {
        mView?.let {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> onImageTint(it as? ImageView, mColor)

                "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(it as? TextView, mColor)

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(it, mColor)

                "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(it as? TextView, mColor, mColor)

                "BACKGROUND_TEXT_DRAWABLE_XML_COLOR_ALPHA" -> onTextWithAlpha(it as? TextView, mColor, alpha)

                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(it, mColor, alpha)
            }
        }

    }

    constructor(mView: View?, mColor: Int?, opt: String?) {
        mView?.let {
            try {
                when (opt) {
                    "CHG_XML_IMAGE_COLOR" -> onImageTint(it as? ImageView, mColor)

                    "BACKGROUND_XML_TEXT_COLOR" -> onTextTint(it as? TextView, mColor)

                    "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(it, mColor)

                    "BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(it as? TextView, mColor, mColor)


                    "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(it, mColor, 25)
                }
            } catch (e: Exception) {
                when (opt) {
                    "CHG_XML_IMAGE_COLOR" -> onImageTint(it as? ImageView)

                    "BACKGROUND_XML_TEXT_COLOR","BACKGROUND_DRAWABLE_XML_COLOR" -> onTextTint(it as? TextView)

                    "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(it)
                }
            }
        }

    }

    constructor(mView: View?, cornerRadius: Float?) {

        val gradient = mView?.background?.mutate() as GradientDrawable

        mView.let {
            val radiusInDp = cornerRadius
            val radiusInPx = radiusInDp?.times(it.resources.displayMetrics.density)
            if (radiusInPx != null) {
                gradient.cornerRadius = radiusInPx
            }
        }

    }

    private fun onImageTint(imageView: ImageView?, color: Int? = null) {
        imageView?.let {
            it.setColorFilter(
                ContextCompat.getColor(it.context, color ?: R.color.gray_color),
                PorterDuff.Mode.SRC_IN)
        }

    }

    private fun onTextTint(textView: TextView?, color: Int? = null) {

//        IsLog(TAG,"color=============$color")
        val drawable = textView?.background?.mutate()

        textView?.let {
            val finalColor = ContextCompat.getColor(it.context, color ?: R.color.gray_color)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable?.colorFilter = BlendModeColorFilter(finalColor, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                drawable?.setColorFilter(finalColor, PorterDuff.Mode.SRC_IN)
            }
        }


    }
    private fun onTextTint(textView: TextView?, color: Int?,color1: Int?,) {

        textView?.let {
            val gradient = it.background?.mutate() as GradientDrawable
            gradient.colors = intArrayOf(
                ContextCompat.getColor(it.context, color ?: R.color.gray_color), // start color
                ContextCompat.getColor(it.context, color ?: R.color.gray_color)  // end color
            )
        }
    }
    private fun onTextWithAlpha(textView: TextView?, color: Int?,opacity: Int?) {
        val colorWithAlpha = ColorUtils.setAlphaComponent(color!!, opacity!!)
        val background = textView?.background?.mutate() as GradientDrawable
        background.setColor(colorWithAlpha)
    }

    fun onBgMutate(mView: View?) {
        mView?.let {
            val color = ContextCompat.getColor(it.context, R.color.gray_color)
            it.background?.mutate()?.let { drawable ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                it.background = drawable
            }
        }
    }

    fun onBgMutate(mView: View?, @ColorRes customColor: Int?) {
        mView?.let {
            if (customColor!=null) {
                val color = ContextCompat.getColor(it.context, customColor) // 🔹 resolve resource to real color
                it.background?.mutate()?.let { drawable ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
                    } else {
                        @Suppress("DEPRECATION")
                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                    }
                    it.background = drawable
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

