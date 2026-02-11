package bottomDlg

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ui.helper.R
import net.cachapa.expandablelayout.ExpandableLayout

class ErrorPopupBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(
            showMsg: String?,
            requestUrl: String?,
            requestValues: String?
        ): ErrorPopupBottomSheet {
            return ErrorPopupBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("msg", showMsg)
                    putString("url", requestUrl)
                    putString("req", requestValues)
                }
            }
        }
    }

    private val showMsg by lazy { arguments?.getString("msg") }
    private val requestUrl by lazy { arguments?.getString("url") }
    private val requestValues by lazy { arguments?.getString("req") }

    private var llApiResponse: LinearLayout? = null
    private var tvResponse: TextView? = null
    private var ivResponseArrow: ImageView? = null
    private var llApiRequest: LinearLayout? = null
    private var tvRequest: TextView? = null
    private var ivRequestArrow: ImageView? = null
    private var expResponse: ExpandableLayout? = null
    private var expRequest: ExpandableLayout? = null
    private var tvApply: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.dlg_on_api_error, container, false)

        llApiResponse = view.findViewById(R.id.ll_api_response)
        tvResponse = view.findViewById(R.id.tv_response)
        ivResponseArrow = view.findViewById(R.id.iv_api_response_drop_down_icon)

        llApiRequest = view.findViewById(R.id.ll_api_request)
        tvRequest = view.findViewById(R.id.tv_request)
        ivRequestArrow = view.findViewById(R.id.iv_api_request_drop_down_icon)

        expResponse = view.findViewById(R.id.exp_api_response)
        expRequest = view.findViewById(R.id.exp_api_request)

        tvApply = view.findViewById(R.id.tv_apply)

        setHtmlText(tvResponse, showMsg)
        setHtmlText(tvRequest, "$requestUrl <br><br> $requestValues")

        expResponse?.expand()
        ivResponseArrow?.rotation = -90f

        expRequest?.collapse()
        ivRequestArrow?.rotation = 90f

        llApiResponse?.setOnClickListener(this)
        llApiRequest?.setOnClickListener(this)
        tvApply?.setOnClickListener(this)

        tvResponse?.setOnLongClickListener {
            copyText(it.context, tvResponse?.text.toString())
            true
        }

        tvRequest?.setOnLongClickListener {
            copyText(it.context, tvRequest?.text.toString())
            true
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            attributes.windowAnimations = R.style.BottomDialogsAnimation
        }

        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isDraggable = true
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ll_api_response -> {
                if (expResponse?.isExpanded == true) {
                    expResponse?.collapse()
                    ivResponseArrow?.rotation = 90f
                } else {
                    expResponse?.expand()
                    ivResponseArrow?.rotation = -90f
                }
            }

            R.id.ll_api_request -> {
                if (expRequest?.isExpanded == true) {
                    expRequest?.collapse()
                    ivRequestArrow?.rotation = 90f
                } else {
                    expRequest?.expand()
                    ivRequestArrow?.rotation = -90f
                }
            }

            R.id.tv_apply -> dismiss()
        }
    }

    private fun copyText(context: Context, text: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText("api", text))
    }
}
