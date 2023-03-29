package id.comrade.services.webservice;

import java.util.List;

import id.comrade.model.Obat;
import id.comrade.model.RootResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ObatApi {
    @GET("/v2/obat/page")
    Single<Response<RootResponse<List<Obat>>>> getObats();
}
