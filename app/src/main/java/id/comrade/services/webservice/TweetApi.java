package id.comrade.services.webservice;

import java.util.List;

import id.comrade.model.RootResponse;
import id.comrade.model.Tweet;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface TweetApi {
    @GET("/sentiment/id/page/0")
    Single<Response<RootResponse<List<Tweet>>>> getTweet();
}
