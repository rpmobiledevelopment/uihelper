package apiController

import android.app.Activity
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre

object ReferApi : GlobalData {

    private val TAG: String = ReferApi::class.java.simpleName

    fun returnApi(mActivity: Activity?): ApiInterface {
        val apiUrl = SharedPre.onReturnText(mActivity, GlobalData.TAG_BASE_URL)
        return ApiClients.getClient(apiUrl).create(ApiInterface::class.java)
    }

    @JvmStatic
    fun returnApiCommon(mActivity: Activity?): ApiInterface {
        val apiUrl = SharedPre.onReturnText(mActivity, GlobalData.TAG_BASE_URL)
        return ApiClients.getClientCommon(apiUrl).create(ApiInterface::class.java)
    }

    @JvmStatic
    fun returnApiLocalCommon(mActivity: Activity?): ApiInterface {
        val apiUrl = SharedPre.onReturnText(mActivity, GlobalData.TAG_BASE_URL)
        return ApiClients.getClientCommon(apiUrl).create(ApiInterface::class.java)
    }

    @JvmStatic
    fun returnApiName(mActivity: Activity?): String? {
        return SharedPre.onReturnText(mActivity, GlobalData.TAG_BASE_URL)
    }
}
