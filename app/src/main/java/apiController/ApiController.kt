package apiController

import android.app.Activity
import apiController.ReferApi.returnApiCommon
import apiController.ReferApi.returnApiLocalCommon
import apiController.ReferApi.returnApiName
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre
import com.rp.uihelpher.log.IsLog
import okhttp3.MultipartBody
import okhttp3.Request
import onInteface.OnInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executors

class ApiController(private val mActivity: Activity) : GlobalData {
    private val TAG: String = ApiController::class.java.simpleName

    fun doGet(apiName: String?, listener: OnInterface.CallbackListener, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon(mActivity).doGetApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), apiName)

        dbResCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 200) {
                    if (response.isSuccessful) {
                        listener.onFetchProgress(ApiClients.getResponseString(), apiNamePageRef)
                        listener.onFetchComplete("API_RESPONSE", apiNamePageRef)
                    } else {
                        listener.onFetchComplete("SERVER_ERROR", apiNamePageRef)
                    }
                } else {
                    listener.onFetchComplete("SERVER_ERROR", apiNamePageRef)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete("ERROR", apiNamePageRef)
            }
        })
    }

    fun doPostMethod(listener: OnInterface.CallbackListener,
        passParaMap: MutableMap<String?, String?>?, apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), passParaMap, apiName)

        dbResCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 200) {
                    if (response.isSuccessful) {
                        listener.onFetchProgress(ApiClients.getResponseString(), apiNamePageRef)
                        listener.onFetchComplete("API_RESPONSE", apiNamePageRef)
                    } else {
                        listener.onFetchComplete("SERVER_ERROR", apiNamePageRef)
                    }
                } else {
                    listener.onFetchComplete("SERVER_ERROR", apiNamePageRef)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete("ERROR", apiNamePageRef)
            }
        })
    }

    fun inPostBackground(listener: OnInterface.CallbackListener, passParaMap: MutableMap<String?, String?>,
        apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), passParaMap, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall.request().url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(
            TAG,
            "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(
                mActivity,
                GlobalData.TAG_BEAR_TOKEN
            )
        )

        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall.execute() // Synchronous execution
                if (response.isSuccessful) {
                    mActivity.runOnUiThread {
                        try {
                            listener.onFetchProgress(ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete("API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            }
        }
        executorService.shutdown()
    }

    fun inPostDownload(listener: OnInterface.CallbackListener, passParaMap: MutableMap<String?, String?>,
        apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), passParaMap, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall.request().url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(
            TAG,
            "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(
                mActivity,
                GlobalData.TAG_BEAR_TOKEN
            )
        )
        //
        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall.execute() // Synchronous execution
                if (response.isSuccessful) {
                    mActivity.runOnUiThread {
                        try {
                            listener.onFetchProgress(ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete("API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            }
        }
        executorService.shutdown()
    }


    fun inImgUpload(listener: OnInterface.CallbackListener, builder: MultipartBody.Builder,
        apiName: String?, apiNamePageRef: String?) {
        val client = ApiClients.getUnsafeOkHttpClient().build()
        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val request = Request.Builder()
                    .url(returnApiName(mActivity) + apiName)
                    .post(builder.build())
                    .addHeader("Authorization",
                        "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN))
                    .build()

                val response = client.newCall(request).execute()

                IsLog(TAG, "response===================$response")

                if (response.isSuccessful) {
                    checkNotNull(response.body)
                    val responseBody = response.body?.string()
                    mActivity.runOnUiThread {
                        try {
                            listener.onFetchProgress(responseBody, apiNamePageRef)
                            listener.onFetchComplete("SUCCESS", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                    IsLog(TAG, "Image deleted successfully====l;jkl;kl;jkl;=====$responseBody")
                } else {
                    mActivity.runOnUiThread {
                        try {
                            listener.onFetchProgress("", apiNamePageRef)
                            listener.onFetchComplete("FAILURE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }

                    IsLog(TAG, "Image deletion failed:----jkl;jkl;---------" + response.message)
                }
                response.close()
            } catch (e: IOException) {
                IsLog(TAG, "IOException===================" + e.message)
                e.printStackTrace()
            }
        }

        executorService.shutdown()
    }
}
