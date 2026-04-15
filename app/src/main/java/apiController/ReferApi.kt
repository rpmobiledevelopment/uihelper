package apiController

import com.ui.helper.constant.GlobalData

object ReferApi : GlobalData {

    private val TAG: String = ReferApi::class.java.simpleName

    fun returnApi(): ApiInterface {
        return ApiClients.getClient(GlobalData.TAG_BASE_URL).create(ApiInterface::class.java)
    }
    @JvmStatic
    fun returnApiCommon(): ApiInterface {
        return ApiClients.getClientCommon(GlobalData.TAG_BASE_URL).create(ApiInterface::class.java)
    }

}
