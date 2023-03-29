package id.comrade.repositories;

import java.util.List;

import id.comrade.model.Obat;
import id.comrade.model.RootResponse;
import id.comrade.services.webservice.ObatApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ObatCache extends Cache<Obat> {
    private static ObatCache instance;

    private ObatCache() {
    }

    public static ObatCache getInstance() {
        if (instance == null) {
            instance = new ObatCache();
        }

        return instance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected Single<List<Obat>> retrieve() {
        return RetrofitService.getInstance()
                .create(ObatApi.class)
                .getObats()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Obat>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }

                    return Single.just(rootResponse.getResult());
                });
    }
}
