package id.comrade.services.webservice;

import java.util.List;

import id.comrade.model.Article;
import id.comrade.model.RootResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleApi {
    @GET("/posting/kategori/{kategori}/id/page/0")
    Single<Response<RootResponse<List<Article>>>> getPosting(@Path("kategori") String kategori);
}
