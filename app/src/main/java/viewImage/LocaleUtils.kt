package viewImage

import android.content.Context
import com.ui.helper.constant.GlobalData
import com.ui.helper.localStorage.SharedPre
import myApp.LocaleHelper

object LocaleUtils {
    fun getLocalizedContext(context: Context?): Context {
        return LocaleHelper.onAttach(context!!, GlobalData.isSelectedLanguage)!!
    }
}
