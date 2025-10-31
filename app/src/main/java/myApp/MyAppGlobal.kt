package myApp

import android.app.Application
import android.content.Context
import io.paperdb.Paper

class MyAppGlobal : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }

    companion object {
        @JvmStatic
        var lng: String? = "EN"
    }
}