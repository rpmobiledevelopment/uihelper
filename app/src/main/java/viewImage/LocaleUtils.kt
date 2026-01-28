package viewImage

import android.content.Context
import com.ui.helper.constant.GlobalData
import com.ui.helper.localStorage.SharedPre
import myApp.LocaleHelper

object LocaleUtils {
    fun getLocalizedContext(context: Context?): Context {
        val lang = SharedPre.getDef(context, GlobalData.TAG_SELECT_LANGUAGE)
        return LocaleHelper.onAttach(context!!, lang)!!
    }
}
