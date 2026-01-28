package apiController

import com.squareup.moshi.Moshi
import com.ui.helper.constant.GlobalData
import com.ui.helper.log.IsLog
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClients : GlobalData {

    private val TAG: String = ApiClients::class.java.simpleName

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    fun getClient(baseUrl: String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl ?: "")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(unsafeOkHttpClient.build())
            .build()
    }

    fun getClientCommon(baseUrl: String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl ?: "")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(unsafeOkHttpClient.build())
            .build()
    }

    private val unsafeOkHttpClient: OkHttpClient.Builder
        get() = builder
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

    private val builder: OkHttpClient.Builder
        get() {
            val builder = OkHttpClient.Builder()

            if (GlobalData.TAG_API_PIN) {
                val pinner = CertificatePinner.Builder()
                    .add(GlobalData.TAG_API_PIN_BASE_URL, GlobalData.TAG_API_PIN_SHA_256)
                    .build()
                builder.certificatePinner(pinner)
            }

            // 📦 Cache Interceptor
            builder.addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=60")
                    .build()
            }

            // ⚠️ Debug interceptor (optimize this)
            builder.addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                val raw = response.peekBody(1024 * 1024).string() // LIMIT SIZE
                IsLog("RAW_API", raw)

                val key = request.url.toString()
                RawStorage.map[key] = raw

                response
            }

            return builder
        }
}
