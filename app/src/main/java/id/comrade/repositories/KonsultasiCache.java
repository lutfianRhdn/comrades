package id.comrade.repositories;

import android.app.Application;

import java.util.List;
import java.util.Map;

import id.comrade.model.Konsultasi;
import id.comrade.model.ListKonsultasiResponse;
import id.comrade.model.RootResponse;
import id.comrade.services.database.AppDatabase;
import id.comrade.services.database.KonsultasiDao;
import id.comrade.services.webservice.KonsultasiApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class KonsultasiCache extends Cache<Konsultasi> {
    private static KonsultasiCache instance;

    private KonsultasiApi api;
    private KonsultasiDao dao;

    private KonsultasiCache(Application app) {
        api = RetrofitService.getInstance().create(KonsultasiApi.class);
        dao = AppDatabase.getAppDatabase(app).getKonsultasiDao();
    }

    public static KonsultasiCache getInstance(Application app) {
        if (instance == null) {
            instance = new KonsultasiCache(app);
        }

        return instance;
    }

    @Override
    public Single<List<Konsultasi>> retrieve() {
        return dao.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    setCache(response);
                    return Single.just(response);
                });
    }

    public Single<Response<RootResponse<Konsultasi>>> send(Map<String, String> body) {
        return api.sendChat(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Response<RootResponse<KonsultasiApi.KonsultasiResponse>>> getChatKonsultasi(Map<String, String> body) {
        return api.getChatKonsultasi(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Long> save(Konsultasi konsultasi) {
        return Single.fromCallable(() -> dao.insert(konsultasi))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
