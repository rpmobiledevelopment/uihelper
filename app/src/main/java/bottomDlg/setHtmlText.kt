package bottomDlg

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView


fun setHtmlText(textView: TextView?, htmlText: String?) {
//    textView.text = convertHtmlToSpannable(htmlText)

    val spannedText: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlText)
    }
    textView?.text = spannedText
}
