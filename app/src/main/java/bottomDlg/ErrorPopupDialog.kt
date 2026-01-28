package bottomDlg

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ui.helper.R
import net.cachapa.expandablelayout.ExpandableLayout

class ErrorPopupDialog(private val mActivity: Activity?,var showMsg : String?,
                       var requestUrl : String?,var requestValues : String?) : View.OnClickListener {

    private var TAG: String? = ErrorPopupDialog::class.simpleName
    private var dialog: BottomSheetDialog? = null
    private var ll_api_response: LinearLayout? = null
    private var tv_api_response: TextView? = null
    private var iv_api_response_drop_down_icon: ImageView? = null
    private var tv_response: TextView? = null
    private var ll_api_request: LinearLayout? = null
    private var tv_api_request: TextView? = null
    private var iv_api_request_drop_down_icon: ImageView? = null
    private var tv_request: TextView? = null
    private var exp_api_response: ExpandableLayout? = null
    private var exp_api_request: ExpandableLayout? = null
    private var tv_apply: TextView? = null

    init {
        onShareDlg()
    }

    fun onShareDlg() {
        mActivity?.let {
            dialog = BottomSheetDialog(it)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

            val convertView = it.layoutInflater.inflate(R.layout.dlg_on_api_error, null)

            ll_api_response = convertView?.findViewById(R.id.ll_api_response)
            tv_api_response = convertView?.findViewById(R.id.tv_api_response)
            iv_api_response_drop_down_icon = convertView?.findViewById(R.id.iv_api_response_drop_down_icon)
            tv_response = convertView?.findViewById(R.id.tv_response)

            ll_api_request = convertView?.findViewById(R.id.ll_api_request)
            tv_api_request = convertView?.findViewById(R.id.tv_api_request)
            iv_api_request_drop_down_icon = convertView?.findViewById(R.id.iv_api_request_drop_down_icon)
            tv_request = convertView?.findViewById(R.id.tv_request)

            exp_api_response = convertView?.findViewById(R.id.exp_api_response)
            exp_api_request = convertView?.findViewById(R.id.exp_api_request)


            exp_api_response?.expand()
            iv_api_response_drop_down_icon?.rotation = -90f

            exp_api_request?.collapse()
            iv_api_request_drop_down_icon?.rotation = 90f

            tv_apply = convertView?.findViewById(R.id.tv_apply)

            setHtmlText(tv_response, showMsg)

            setHtmlText(tv_request, "$requestUrl <br><br> $requestValues")

            tv_apply?.text = ContextCompat.getString(it,R.string.error_cancel_btn)

            ll_api_response?.setOnClickListener(this)
            ll_api_request?.setOnClickListener(this)
            tv_apply?.setOnClickListener(this)

            tv_response?.setOnLongClickListener { v->
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                toCopy(tv_response?.text.toString())
                true
            }
            tv_request?.setOnLongClickListener { v->
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                toCopy(tv_request?.text.toString())
                true
            }

            dialog?.setContentView(convertView)

            onShowDlg()
        }

    }

    private fun toCopy(text: String) {
        val cm = mActivity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val cd = ClipData.newPlainText("label", text)
        cm.setPrimaryClip(cd)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_api_response -> {
                if (exp_api_response?.isExpanded == true) {
                    exp_api_response?.collapse()
                    iv_api_response_drop_down_icon?.rotation = 90f
                } else {
                    exp_api_response?.expand()
                    iv_api_response_drop_down_icon?.rotation = -90f
                }
            }
            R.id.ll_api_request -> {
                if (exp_api_request?.isExpanded == true) {
                    exp_api_request?.collapse()
                    iv_api_request_drop_down_icon?.rotation = 90f
                } else {
                    exp_api_request?.expand()
                    iv_api_request_drop_down_icon?.rotation = -90f
                }
            }
            R.id.tv_apply -> {
                dialog?.dismiss()
            }
        }
    }

    private fun onShowDlg() {
        if (dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val bottomSheet = dialog?.findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)

            val lp = WindowManager.LayoutParams()
            val window = dialog?.window
            lp.copyFrom(window?.attributes)

            val size = Point()
            mActivity?.windowManager?.defaultDisplay?.getSize(size)
            lp.width = size.x
            lp.height = size.y
            window?.attributes = lp

            // Set animation if needed
            dialog?.window?.attributes?.windowAnimations = R.style.BottomDialogsAnimation
        }
        dialog?.behavior?.isDraggable = true
        dialog?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        dialog?.setCancelable(true)
        dialog?.show()
    }

}
