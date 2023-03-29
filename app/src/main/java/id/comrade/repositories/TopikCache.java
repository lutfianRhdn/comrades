package id.comrade.repositories;

import java.util.List;

import id.comrade.model.RootResponse;
import id.comrade.model.Topic;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.InformationApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopikCache extends Cache<Topic> {

    private static TopikCache sInstance;

    public static TopikCache getInstance() {
        if (sInstance == null) {
            sInstance = new TopikCache();
        }

        return sInstance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Single<List<Topic>> retrieve() {
        return RetrofitService.getInstance()
                .create(InformationApi.class)
                .getTopics()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Topic>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }

                    return Single.just(rootResponse.getResult());
                });
    }
}
