package apiController

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("{ATNDB}")
    fun doGetApi(
        @Header("Authorization") headers: String?,
        @Path("ATNDB", encoded = true) apiName: String?
    ): Call<Void?>?

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("{ATNDB}")
    fun doPostApi(@Header("Authorization") headers: String?,
                  @FieldMap fieldMap: MutableMap<String?, Any?>?,
                  @Path("ATNDB", encoded = true) apiName: String?): Call<Void?>?

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("{ATNDB}")
    fun doPostApi(
        @Header("Authorization") headers: String?,
        @Body body: RequestBody?, @Path("ATNDB", encoded = true) apiName: String?
    ): Call<Void?>?
}
