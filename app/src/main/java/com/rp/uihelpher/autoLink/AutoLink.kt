package com.rp.uihelpher.autoLink

import android.text.Editable
import android.text.TextWatcher
import android.text.method.ArrowKeyMovementMethod
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.EditText
import android.widget.TextView

class AutoLink {

    constructor(tv_auto_link: TextView) {
        tv_auto_link.linksClickable = true
        tv_auto_link.autoLinkMask = Linkify.ALL
        tv_auto_link.movementMethod = LinkMovementMethod.getInstance()
        tv_auto_link.movementMethod = ArrowKeyMovementMethod.getInstance()
        Linkify.addLinks(tv_auto_link, Linkify.ALL)
    }

    constructor(et_auto_link: EditText, addTextChangedListener: String) {
        et_auto_link.linksClickable = true
        et_auto_link.autoLinkMask = Linkify.ALL
        et_auto_link.movementMethod = LinkMovementMethod.getInstance()
        et_auto_link.movementMethod = ArrowKeyMovementMethod.getInstance()
        Linkify.addLinks(et_auto_link, Linkify.ALL)

        if (addTextChangedListener == "YES") {
            et_auto_link.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    Linkify.addLinks(s, Linkify.ALL)
                }
            })
        }
    }
}
