package apiController;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {

    private String responseString;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // Capture the response body
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            responseString = responseBody.string();
            return response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), responseString))
                    .build();
        } else {
            return response;
        }
    }

    public String getResponseString() {
        return responseString;
    }
}