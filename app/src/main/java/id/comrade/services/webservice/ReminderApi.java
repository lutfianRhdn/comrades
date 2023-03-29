package id.comrade.services.webservice;

import com.google.gson.JsonObject;

import java.util.List;

import id.comrade.model.Reminder;
import id.comrade.model.RootResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReminderApi {
    @POST("/v2/reminder")
    Single<JsonObject> saveReminder(@Body Reminder reminder);

    @HTTP(method = "DELETE", path = "/v2/reminder", hasBody = true)
    Single<JsonObject> deleteReminder(@Body Reminder reminder);

    @PUT("/v2/reminder")
    Single<JsonObject> updateReminder(@Body Reminder reminder);

    @GET("/v2/reminder/user/{userId}")
    Single<RootResponse<List<Reminder>>> findAll(@Path("userId") int userId);
}
