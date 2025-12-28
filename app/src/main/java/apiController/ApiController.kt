package apiController

import android.app.Activity
import apiController.ReferApi.returnApiCommon
import apiController.ReferApi.returnApiLocalCommon
import apiController.ReferApi.returnApiName
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre
import com.rp.uihelpher.log.IsLog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import onInteface.OnInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.Executors

class ApiController(private val mActivity: Activity?) : GlobalData {
    private val TAG: String = ApiController::class.java.simpleName

    fun doGet(apiName: String?, listener: OnInterface.CallbackListener, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon(mActivity).doGetApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), apiName)

        dbResCall?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                mActivity?.runOnUiThread {
                    try {
                        if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } else {
                            listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                        }
                    } catch (e : SocketTimeoutException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e : NumberFormatException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: NullPointerException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: Exception) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    }
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                }
            }
        })
    }

    fun doPostMethod(listener: OnInterface.CallbackListener,
                     passParaMap: MutableMap<String?, Any?>?, apiName: String?, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE),passParaMap, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                mActivity?.runOnUiThread {
                    try {
                        if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } else {
                            listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                        }
                    } catch (e : SocketTimeoutException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e : NumberFormatException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: NullPointerException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: Exception) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    }
                }

            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    fun doPostMethod(listener: OnInterface.CallbackListener,headerParaMap: MutableMap<String?, Any?>?,
                     passParaMap: MutableMap<String?, Any?>?, apiName: String?, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon(mActivity).doPostApi(
            headerParaMap,passParaMap, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                mActivity?.runOnUiThread {
                    try {
                        if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } else {
                            listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                        }
                    } catch (e : SocketTimeoutException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e : NumberFormatException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: NullPointerException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: Exception) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    }
                }

            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    fun inPostBackground(listener: OnInterface.CallbackListener, passParaMap: MutableMap<String?, Any?>,
                         apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE),passParaMap, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall?.request()?.url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(TAG, "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN))

        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall?.execute() // Synchronous execution
                if (response?.isSuccessful == true) {
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity?.runOnUiThread {
                        listener.onFetchComplete(response?.code(), "SERVER_ERROR", apiNamePageRef)
                    }
                    IsLog(TAG, "Image deletion failed: " + response?.message())
                }
            } catch (e: NullPointerException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(
                        700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700, "SERVER_ERROR", apiNamePageRef)
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            }
        }
        executorService.shutdown()
    }

    fun inPostDownload(listener: OnInterface.CallbackListener, passParaMap: MutableMap<String?, Any?>,
                       apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), passParaMap, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall?.request()?.url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(TAG, "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN))
        //
        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall?.execute() // Synchronous execution
                if (response?.isSuccessful == true) {
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity?.runOnUiThread {
                        listener.onFetchComplete(response?.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response?.message())
                }
            } catch (e: NullPointerException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700, "SERVER_ERROR", apiNamePageRef)
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            }
        }
        executorService.shutdown()
    }

    // Body
    fun doPostMethod(listener: OnInterface.CallbackListener,
                     passParaMap: JSONObject, apiName: String?, apiNamePageRef: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val dbResCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), requestBody, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                IsLog(TAG,"response=================${response.code()}")
                IsLog(TAG,"response========body=========${response.body()}")
                mActivity?.runOnUiThread {
                    try {
                        if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } else {
                            listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                        }
                    } catch (e : SocketTimeoutException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e : NumberFormatException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: NullPointerException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: Exception) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    }
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    var currentCall: Call<Void?>? = null

    // Body
    fun doPostMethod(listener: OnInterface.CallbackListener,
                     passParaMap: JSONObject, apiName: String?, apiNamePageRef: String?, previousCancelled: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        if (previousCancelled == "CANCELLED") {
            // Cancel previous
            currentCall?.takeIf { it.isExecuted && !it.isCanceled }?.cancel()
            currentCall?.cancel()
        }

        currentCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), requestBody, apiName)

        IsLog(TAG,"dbResCall=================${currentCall?.request()?.url}")

        currentCall?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
//                IsLog(TAG,"response=================${response.code()}")
//                IsLog(TAG,"response========body=========${response.body()}")
                mActivity?.runOnUiThread {
                    try {
                        if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } else {
                            listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                        }
                    } catch (e : SocketTimeoutException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e : NumberFormatException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: NullPointerException) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    } catch (e: Exception) {
                        listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                    }
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)

                if (call.isCanceled) {
                    IsLog(TAG, "Call canceled")
                    return
                }

                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }


    // 🔹 Add a public function to cancel current call
    fun cancelCurrentCall() {
        currentCall?.cancel()
    }

    fun inPostBackground(listener: OnInterface.CallbackListener, passParaMap: JSONObject,
                         apiName: String?, apiNamePageRef: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), requestBody, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall?.request()?.url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(TAG, "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN))

        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall?.execute() // Synchronous execution
                if (response?.isSuccessful == true) {
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity?.runOnUiThread {
                        listener.onFetchComplete(response?.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response?.message())
                }
            } catch (e: NullPointerException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,
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

    fun inPostDownload(listener: OnInterface.CallbackListener, passParaMap: JSONObject,
                       apiName: String?, apiNamePageRef: String?) {


        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())


        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN),
            SharedPre.getDef(mActivity, GlobalData.TAG_SELECT_LANGUAGE), requestBody, apiName
        )

        IsLog(TAG, "dbResCall==========" + dbResCall?.request()?.url)
        IsLog(TAG, "passParaMap=====passParaMap=====$passParaMap")
        IsLog(TAG, "passParaMap=====TAG_ACC_KEY=====" + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN))
        //
        val executorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            try {
                val response = dbResCall?.execute() // Synchronous execution
                if (response?.isSuccessful == true) {
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity?.runOnUiThread {
                        listener.onFetchComplete(response?.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response?.message())
                }
            } catch (e: NullPointerException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity?.runOnUiThread {
                    listener.onFetchComplete(700,
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
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code,responseBody, apiNamePageRef)
                            listener.onFetchComplete(response.code,"SUCCESS", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                    IsLog(TAG, "Image deleted successfully====l;jkl;kl;jkl;=====$responseBody")
                } else {
                    mActivity?.runOnUiThread {
                        try {
                            listener.onFetchProgress(response.code,"", apiNamePageRef)
                            listener.onFetchComplete(response.code,"FAILURE", apiNamePageRef)
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
