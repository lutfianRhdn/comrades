package id.comrade.services.webservice;

import java.util.List;

import id.comrade.model.Posting;
import id.comrade.model.RootResponse;
import id.comrade.model.Topic;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InformationApi {
    @GET("/v2/topik")
    Single<Response<RootResponse<List<Topic>>>> getTopics();

    @GET("/posting/kategori/{kategori}/id/page/0")
    Single<Response<RootResponse<List<Posting>>>> getPosting(@Path("kategori") String kategori);
}
