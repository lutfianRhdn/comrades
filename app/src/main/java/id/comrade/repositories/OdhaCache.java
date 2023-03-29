package id.comrade.repositories;

import java.util.List;

import id.comrade.model.RootResponse;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.UserApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OdhaCache extends Cache<User> {
    private static OdhaCache instance;

    private OdhaCache() {
    }

    public static OdhaCache getInstance() {
        if (instance == null) {
            instance = new OdhaCache();
        }

        return instance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected Single<List<User>> retrieve() {
        return RetrofitService.getInstance()
                .create(UserApi.class)
                .getOdha()
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
