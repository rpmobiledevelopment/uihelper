package apiController;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("{ATNDB}")
    Call<Void> doGetApi(@Header("Authorization") String headers,
                        @Path("ATNDB") String apiName);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("{ATNDB}")
    Call<Void> doPostApi(@Header("Authorization") String headers,
                         @FieldMap Map<String, String> fieldMap,
                         @Path("ATNDB") String apiName);
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("{ATNDB}")
    Call<Void> doPostApi(@Header("Authorization") String headers,
                         @Body RequestBody body, @Path("ATNDB") String apiName);

}
