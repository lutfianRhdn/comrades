package id.comrade.services.webservice;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import id.comrade.model.RootResponse;
import id.comrade.model.User;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {
    @GET("/v2/sahabatodha")
    Single<Response<RootResponse<List<User>>>> getSahabatOdha();

    @GET("/v2/odha")
    Single<Response<RootResponse<List<User>>>> getOdha();

    @POST("/v2/login")
    Single<Response<RootResponse<User>>> login(@Body User body);

    @POST("/v2/register")
    Single<Response<RootResponse<User>>> register(@Body User body);

    @PUT("/v2/user")
    Single<Response<RootResponse<User>>> edit(@Body User user);

    @PUT("/v2/registrationToken")
    Single<Response<RootResponse<Integer>>> updateFcmToken(@Body User user);
}
