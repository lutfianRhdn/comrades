package id.comrade.repositories;

import java.util.List;

import id.comrade.model.Posting;
import id.comrade.model.RootResponse;
import id.comrade.services.webservice.InformationApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsCache extends Cache<Posting> {

    private static NewsCache instance;

    public static NewsCache getInstance() {
        if (instance == null) {
            instance = new NewsCache();
        }

        return instance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected Single<List<Posting>> retrieve() {
        return RetrofitService.getInstance()
                .create(InformationApi.class)
                .getPosting("Berita")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Posting>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }

//                    Log.d("cek", rootResponse.getResult().get(0).getJudul());
                    return Single.just(rootResponse.getResult());
                });
    }
}
