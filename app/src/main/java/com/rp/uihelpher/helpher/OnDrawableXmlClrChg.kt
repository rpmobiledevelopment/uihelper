package com.rp.uihelpher.helpher

import android.content.res.Resources
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
    constructor(mActivity: android.app.Activity, mView: View, mColor: Int, opt: String, width: Int) {
        when (opt) {
            "BACKGROUND_XML_COLOR" -> {
                val backgroundGradient = mView.background.mutate() as GradientDrawable
                val strokeColor = ContextCompat.getColor(mActivity, mColor) // safe way
                backgroundGradient.setStroke(width, strokeColor)
            }
            "CHG_XML_IMAGE_COLOR" -> {
                val imageView = mView as ImageView
                onBgMutate(imageView,mColor)
            }

            "BACKGROUND_XML_TEXT_COLOR" -> {
                val textView = mView as TextView
                onBgMutate(textView,mColor)
            }

            "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mView,mColor)

            "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView,mColor,25)
        }
    }

    constructor(mActivity: android.content.Context, mView: View, mColor: Int, opt: String) {
        try {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> {
                    val imageView = mView as ImageView
                    onBgMutate(imageView,mColor)
                }
                "BACKGROUND_XML_TEXT_COLOR" -> {
                    val textView = mView as TextView
                    onBgMutate(textView,mColor)
                }
                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mView,mColor)

                "BACKGROUND_XML_FULL_COLOR_ALPHA" -> onBgMutate(mView,mColor,25)
            }
        } catch (e: Resources.NotFoundException) {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> {
                    val imageView = mView as ImageView
                    onBgMutate(mActivity,imageView)
                }

                "BACKGROUND_XML_TEXT_COLOR" -> {
                    val textView = mView as TextView
                    onBgMutate(mActivity,textView)
                }

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity,mView)
            }
        } catch (e: java.lang.NullPointerException) {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> {
                    val imageView = mView as ImageView
                    onBgMutate(mActivity,imageView)
                }
                "BACKGROUND_XML_TEXT_COLOR" -> {
                    val textView = mView as TextView
                    onBgMutate(mActivity,textView)
                }
                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity,mView)
            }
        } catch (e: java.lang.NumberFormatException) {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> {
                    val imageView = mView as ImageView
                    onBgMutate(mActivity,imageView)
                }
                "BACKGROUND_XML_TEXT_COLOR" -> {
                    val textView = mView as TextView
                    onBgMutate(mActivity,textView)
                }
                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity,mView)
            }
        } catch (e: java.lang.Exception) {
            when (opt) {
                "CHG_XML_IMAGE_COLOR" -> {
                    val imageView = mView as ImageView
                    onBgMutate(mActivity,imageView)
                }

                "BACKGROUND_XML_TEXT_COLOR" -> {
                    val textView = mView as TextView
                    onBgMutate(mActivity,textView)
                }

                "BACKGROUND_XML_FULL_COLOR" -> onBgMutate(mActivity,mView)
            }
        }
    }

    constructor(mActivity: android.content.Context, mView: android.view.View, mColor: kotlin.Int) {
        try {
            onBgMutate(mView,mColor)
        } catch (e: Resources.NotFoundException) {
            onBgMutate(mActivity,mView)
        } catch (e: java.lang.NullPointerException) {
            onBgMutate(mActivity,mView)
        } catch (e: java.lang.NumberFormatException) {
            onBgMutate(mActivity,mView)
        } catch (e: java.lang.Exception) {
            onBgMutate(mActivity,mView)
        }
    }

    fun onBgMutate(mActivity: android.content.Context, mView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mView.background.mutate().colorFilter = BlendModeColorFilter(
                ContextCompat.getColor(mActivity, R.color.gray_color),
                BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            mView.background.mutate().setColorFilter(
                ContextCompat.getColor(mActivity, R.color.gray_color),
                PorterDuff.Mode.SRC_IN)
        }
    }
    fun onBgMutate(mView: View, customColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mView.background.mutate().colorFilter = BlendModeColorFilter(customColor, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            mView.background.mutate().setColorFilter(customColor, PorterDuff.Mode.SRC_IN)
        }
    }
    fun onBgMutate(mView: View, customColor: Int, opacity: Int) {
        val tintedColor = ColorUtils.setAlphaComponent(customColor, 25) // 10% opacity

        val drawable = mView.background.mutate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(tintedColor, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(tintedColor, PorterDuff.Mode.SRC_IN)
        }
        mView.background = drawable
    }
}
