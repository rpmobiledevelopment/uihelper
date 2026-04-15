package apiController

import android.content.Context
import android.os.Handler
import android.os.Looper
import apiController.ReferApi.returnApiCommon
import bottomDlg.ErrorPopupDialog
import com.ui.helper.constant.GlobalData
import com.ui.helper.log.IsLog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import onInteface.OnInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.SocketTimeoutException

class ApiController : GlobalData {
    private val TAG: String = ApiController::class.java.simpleName

    fun doGet(apiName: String?, listener: OnInterface.CallbackListener, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon().doGetApi(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN, GlobalData.TAG_SELECT_LANGUAGE, apiName)

        dbResCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Handler(Looper.getMainLooper()).post {
                    try {
                        val responseData = response.body() ?: response.errorBody()?.string()

                        if (response.isSuccessful) {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                            }
                        } else {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchProgress(800,responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_FAILURE_RESPONSE", apiNamePageRef)
                            }
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                Handler(Looper.getMainLooper()).post {
                    listener.onFetchComplete(700,"ERROR", apiNamePageRef)
                }
            }
        })
    }

    fun doPostMethod(listener: OnInterface.CallbackListener,
                     passParaMap: MutableMap<String?, Any?>?, apiName: String?, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon().doPostApi(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN,
            GlobalData.TAG_SELECT_LANGUAGE,passParaMap, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Handler(Looper.getMainLooper()).post {
                    try {
                        val responseData = response.body() ?: response.errorBody()?.string()

                        if (response.isSuccessful) {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                            }
                        } else {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchProgress(800,responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_FAILURE_RESPONSE", apiNamePageRef)
                            }
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }
    fun doPostMethod(context : Context?,listener: OnInterface.CallbackListener,
                     passParaMap: MutableMap<String?, Any?>?, apiName: String?, apiNamePageRef: String?) {

        val dbResCall = returnApiCommon().doPostApi(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN,
            GlobalData.TAG_SELECT_LANGUAGE,passParaMap, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Handler(Looper.getMainLooper()).post {
                    try {
                        if (context != null && GlobalData.isApiPopup) {
                            ErrorPopupDialog(context, response.body().toString(), dbResCall.request().url.toString(), "")
                        }
                        val responseData = response.body() ?: response.errorBody()?.string()

                        if (response.isSuccessful) {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                            }
                        } else {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchProgress(800,responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_FAILURE_RESPONSE", apiNamePageRef)
                            }
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    // Body
    fun doPostMethod(listener: OnInterface.CallbackListener,
                     passParaMap: JSONObject, apiName: String?, apiNamePageRef: String?) {

        val requestBody = passParaMap
            .toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val dbResCall = returnApiCommon().doPostApi(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN,GlobalData.TAG_SELECT_LANGUAGE, requestBody, apiName)

        IsLog(TAG,"dbResCall=================${dbResCall?.request()?.url}")

        dbResCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                IsLog(TAG,"response=================${response.code()}")
                IsLog(TAG,"response========body=========${response.body()}")
                Handler(Looper.getMainLooper()).post {
                    try {
                        val responseData = response.body() ?: response.errorBody()?.string()

                        if (response.isSuccessful) {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                            }
                        } else {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchProgress(800,responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_FAILURE_RESPONSE", apiNamePageRef)
                            }
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                IsLog(TAG, "Throwable: " + t.message)
                listener.onFetchComplete(700,"ERROR", apiNamePageRef)
            }
        })
    }

    var currentCall: Call<String?>? = null

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

        currentCall = returnApiCommon().doPostApi(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN,GlobalData.TAG_SELECT_LANGUAGE, requestBody, apiName)

        IsLog(TAG,"dbResCall=================${currentCall?.request()?.url}")

        currentCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
//                IsLog(TAG,"response=================${response.code()}")
//                IsLog(TAG,"response========body=========${response.body()}")
                Handler(Looper.getMainLooper()).post {
                    try {
                        val responseData = response.body() ?: response.errorBody()?.string()

                        if (response.isSuccessful) {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchComplete(response.code(),"SERVER_ERROR", apiNamePageRef)
                            }
                        } else {
                            if (response.code() == 200 || response.code() == 401 || response.code() == 422) {
                                listener.onFetchProgress(response.code(),responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_RESPONSE", apiNamePageRef)
                            } else {
                                listener.onFetchProgress(800,responseData, apiNamePageRef)
                                listener.onFetchComplete(response.code(),"API_FAILURE_RESPONSE", apiNamePageRef)
                            }
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

            override fun onFailure(call: Call<String?>, t: Throwable) {
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


    fun doPostMultipart(
        listener: OnInterface.CallbackListener, passParaMap: MutableMap<String?, Any?>?,
        fileList: List<File>, filesKeyName: String?,apiName: String?, apiNamePageRef: String?) {

        val requestMap = HashMap<String, RequestBody>()

        passParaMap?.forEach { (key, value) ->
            value?.let {
                requestMap[key ?: ""] = it.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            }
        }

        val fileParts = ArrayList<MultipartBody.Part>()

        fileList.forEach { file ->

            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val body = MultipartBody.Part.createFormData(
                filesKeyName.toString(), file.name, requestFile)

            fileParts.add(body)
        }

        val dbResCall = returnApiCommon().doPostMultipartMultiple(
            "Bearer " + GlobalData.TAG_BEAR_TOKEN,
            GlobalData.TAG_SELECT_LANGUAGE, requestMap, fileParts, apiName)

        dbResCall.enqueue(object : Callback<String?> {

            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Handler(Looper.getMainLooper()).post {
                    try {
                        val responseData = response.body() ?: response.errorBody()?.string()
                        listener.onFetchProgress(response.code(), responseData, apiNamePageRef)
                        listener.onFetchComplete(response.code(), "API_RESPONSE", apiNamePageRef)

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

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Handler(Looper.getMainLooper()).post {
                    listener.onFetchComplete(700, "ERROR", apiNamePageRef)
                }

            }
        })
    }
}
