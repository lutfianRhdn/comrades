package id.comrade.services.webservice;

import java.util.List;

import id.comrade.model.Event;
import id.comrade.model.RootResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface EventApi {

    @GET("/event/public/id/page/0")
    Single<Response<RootResponse<List<Event>>>> getEvent();
}
