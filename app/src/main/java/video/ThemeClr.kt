package video

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ui.helper.R
import com.ui.helper.constant.GlobalData
import myApp.LocaleHelper.setLocale
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ThemeClr(mActivity: Activity?) : GlobalData {
    init {
        // Disable automatic fitting to system windows
        WindowCompat.setDecorFitsSystemWindows(mActivity!!.window, false)

        val window = mActivity.window
        val controller = WindowInsetsControllerCompat(window!!, window.decorView)
            mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Set status bar background color
        val color = ContextCompat.getColor(mActivity, R.color.color1)
        window.statusBarColor = color

        // Set light status bar icons if background is light
        controller.isAppearanceLightStatusBars = true

        // Set locale based on saved language
        val langCode = if (GlobalData.isSelectedLanguage == "AR" || GlobalData.isSelectedLanguage == "ar") "ar" else "en"
        setLocale(mActivity, langCode)
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val resources = mActivity?.resources

        val configuration = resources?.configuration
        configuration?.locale = locale
        configuration?.setLayoutDirection(locale)

        resources?.updateConfiguration(configuration, resources?.displayMetrics)

    }
}
