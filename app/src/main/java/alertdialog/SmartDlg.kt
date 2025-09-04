package alertdialog

import alertdialog.OptAnimationLoader.loadAnimation
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rp.uihelpher.R
import java.util.Objects

class SmartDlg(private var mContext: Context, alertType: Int) : Dialog(
    mContext, R.style.progressalert_dialog) {

    private var mDialogView: View? = null
    private val mModalInAnim: AnimationSet?
    private val mModalOutAnim: AnimationSet?
    private val mOverlayOutAnim: Animation?
    private var mErrorXInAnim: AnimationSet?
    private var mTitleText: String? = null
    private var mContentText: String? = null
    private var mShowCancel = false
    private var mShowContent = false
    private var mCancelText: String? = null
    private var mConfirmText: String? = null
    private var mAlertType: Int
    private var mProgressFrame: FrameLayout? = null
    private var graduallyTextView: TextView? = null
    private var iv_logo: ImageView? = null
    private var mCustomImgDrawable: Drawable? = null
    private var mCloseFromCancel = false

    constructor(context: Context) : this(context, NORMAL_TYPE) {
        this.mContext = context
    }

    init {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        mAlertType = alertType
        mErrorXInAnim = loadAnimation(context, R.anim.error_x_in) as AnimationSet?
        mModalInAnim = loadAnimation(context, R.anim.modal_in) as AnimationSet?
        mModalOutAnim = loadAnimation(context, R.anim.modal_out) as AnimationSet?
        mModalOutAnim?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                mDialogView?.visibility = View.GONE
                mDialogView?.post {
                    if (mCloseFromCancel) {
                        super@SmartDlg.cancel()
                    } else {
                        super@SmartDlg.dismiss()
                    }
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        // dialog overlay fade out
        mOverlayOutAnim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                val wlp = window?.attributes
                wlp?.alpha = 1 - interpolatedTime
                window?.attributes = wlp
            }
        }
        mOverlayOutAnim.duration = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progressbar)

        mDialogView = Objects.requireNonNull<Window?>(getWindow()).getDecorView()
            .findViewById(android.R.id.content)
        mProgressFrame = findViewById(R.id.progress_dialog)
        graduallyTextView = findViewById(R.id.tv_title)
        iv_logo = findViewById(R.id.iv_logo)

        iv_logo?.setColorFilter(
            ContextCompat.getColor(
                mContext,
                R.color.snack_bar_color
            ), PorterDuff.Mode.SRC_IN
        )

        setTitleText(mTitleText)
        setContentText(mContentText)
        setCancelText(mCancelText)
        setConfirmText(mConfirmText)
        changeAlertType(mAlertType, true)
    }

    private fun restore() {
        mProgressFrame?.visibility = View.GONE
    }

    private fun playAnimation() {}

    private fun changeAlertType(alertType: Int, fromCreate: Boolean) {
        mAlertType = alertType
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore()
            }
            when (mAlertType) {
                ERROR_TYPE -> {}
                SUCCESS_TYPE -> {}
                WARNING_TYPE -> {}
                CUSTOM_IMAGE_TYPE -> setCustomImage(mCustomImgDrawable)
                PROGRESS_TYPE -> mProgressFrame?.visibility = View.VISIBLE
            }
            if (!fromCreate) {
                playAnimation()
            }
        }
    }

    fun setTitleText(text: String?): SmartDlg {
        mTitleText = text
        return this
    }

    fun setCustomImage(drawable: Drawable?): SmartDlg {
        mCustomImgDrawable = drawable
        return this
    }

    fun setContentText(text: String?): SmartDlg {
        mContentText = text
        if (mContentText != null) {
            showContentText(true)
        }
        return this
    }

    fun showContentText(isShow: Boolean): SmartDlg {
        mShowContent = isShow
        return this
    }

    fun setCancelText(text: String?): SmartDlg {
        mCancelText = text
        return this
    }

    fun setConfirmText(text: String?): SmartDlg {
        mConfirmText = text
        return this
    }

    override fun onStart() {
        mDialogView?.startAnimation(mModalInAnim)
        playAnimation()
    }

    override fun cancel() {
        dismissWithAnimation(true)
    }

    private fun dismissWithAnimation(fromCancel: Boolean) {
        mCloseFromCancel = fromCancel
        mDialogView?.startAnimation(mModalOutAnim)
    }

    companion object {
        const val NORMAL_TYPE: Int = 0
        const val ERROR_TYPE: Int = 1
        const val SUCCESS_TYPE: Int = 2
        const val WARNING_TYPE: Int = 3
        const val CUSTOM_IMAGE_TYPE: Int = 4
        const val PROGRESS_TYPE: Int = 5
    }
}