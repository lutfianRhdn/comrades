package id.comrade.repositories;

import java.util.List;

import id.comrade.model.RootResponse;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.UserApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SahabatOdhaCache extends Cache<User> {

    private static SahabatOdhaCache sInstance;

    private SahabatOdhaCache() {
    }

    public static SahabatOdhaCache getInstance() {
        if (sInstance == null) {
            sInstance = new SahabatOdhaCache();
        }

        return sInstance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Single<List<User>> retrieve() {
        return RetrofitService.getInstance()
                .create(UserApi.class)
                .getSahabatOdha()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<User>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }

                    return Single.just(rootResponse.getResult());
                });
    }
}
