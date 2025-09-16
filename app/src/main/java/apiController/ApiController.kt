package apiController

import android.app.Activity
import apiController.ReferApi.returnApiCommon
import apiController.ReferApi.returnApiLocalCommon
import apiController.ReferApi.returnApiName
import com.google.gson.JsonObject
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre
import com.rp.uihelpher.log.IsLog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
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
                if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                    if (response.isSuccessful) {
                        listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                        listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                    } else {
                        listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                    }
                } else {
                    listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    fun doPostMethod(listener: OnInterface.CallbackListener,
        passParaMap: MutableMap<String?, String?>?, apiName: String?, apiNamePageRef: String?) {
        val dbResCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), passParaMap, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall.request().url}")

        dbResCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                IsLog(TAG,"response=================${response.code()}")
                IsLog(TAG,"response========body=========${response.body()}")
                if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                    if (response.isSuccessful) {
                        listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                        listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                    } else {
                        listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                    }
                } else {
                    listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
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
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(response.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(
                        700,
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
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(response.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
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

    // Body
    fun doPostMethod(listener: OnInterface.CallbackListener,
        passParaMap: JsonObject, apiName: String?, apiNamePageRef: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val dbResCall = returnApiCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), requestBody, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall.request().url}")

        dbResCall.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                IsLog(TAG,"response=================${response.code()}")
                IsLog(TAG,"response========body=========${response.body()}")
                if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                    if (response.isSuccessful) {
                        listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                        listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                    } else {
                        listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                    }
                } else {
                    listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    fun inPostBackground(listener: OnInterface.CallbackListener, passParaMap: JsonObject,
        apiName: String?, apiNamePageRef: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), requestBody, apiName
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
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(response.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
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

    fun inPostDownload(listener: OnInterface.CallbackListener, passParaMap: JsonObject,
        apiName: String?, apiNamePageRef: String?) {


        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())


        val dbResCall = returnApiLocalCommon(mActivity).doPostApi(
            "Bearer " + SharedPre.getDef(mActivity, GlobalData.TAG_BEAR_TOKEN), requestBody, apiName
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
                            listener.onFetchProgress(response.code(),ApiClients.getResponseString(), apiNamePageRef)
                            listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                        } catch (e: NullPointerException) {
                            IsLog(TAG, "IOException: " + e.message)
                            e.printStackTrace()
                        }
                    }
                } else {
                    mActivity.runOnUiThread {
                        listener.onFetchComplete(response.code(),
                            "SERVER_ERROR",
                            apiNamePageRef
                        )
                    }
                    IsLog(TAG, "Image deletion failed: " + response.message())
                }
            } catch (e: NullPointerException) {
                mActivity.runOnUiThread {
                    listener.onFetchComplete(700,
                        "SERVER_ERROR",
                        apiNamePageRef
                    )
                }
                IsLog(TAG, "IOException: " + e.message)
                e.printStackTrace()
            } catch (e: IOException) {
                mActivity.runOnUiThread {
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
                    mActivity.runOnUiThread {
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
                    mActivity.runOnUiThread {
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
