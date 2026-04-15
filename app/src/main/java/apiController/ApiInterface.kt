package apiController

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiInterface {
    @GET("{ATNDB}")
    fun doGetApi(
        @Header("Authorization") headers: String?,@Header("Accept-Language") language: String?,
        @Path("ATNDB", encoded = true) apiName: String?): Call<String?>?

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("{ATNDB}")
    fun doPostApi(@Header("Authorization") headers: String?,@Header("Accept-Language") language: String?,
                  @FieldMap fieldMap: MutableMap<String?, Any?>?,
                  @Path("ATNDB", encoded = true) apiName: String?): Call<String?>?
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("{ATNDB}")
    fun doPostApi(@HeaderMap headerMap: MutableMap<String?, Any?>?,
                  @FieldMap fieldMap: MutableMap<String?, Any?>?,
                  @Path("ATNDB", encoded = true) apiName: String?): Call<String?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("{ATNDB}")
    fun doPostApi(
        @Header("Authorization") headers: String?,@Header("Accept-Language") language: String?,
        @Body body: RequestBody?, @Path("ATNDB", encoded = true) apiName: String?): Call<String?>?

    @Multipart
    @POST
    fun doPostMultipartMultiple(
        @Header("Authorization") token: String?,
        @Header("Accept-Language") language: String?,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part files: List<MultipartBody.Part>?,
        @Url apiName: String?
    ): Call<String?>
}
