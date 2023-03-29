package id.comrade.repositories;

import java.util.List;

import id.comrade.model.Event;
import id.comrade.model.RootResponse;
import id.comrade.services.webservice.EventApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EventCache extends Cache<Event> {
    private static EventCache instance;

    public static EventCache getInstance(){
        if (instance == null){
            instance = new EventCache();
        }
        return instance;
    }

    //
//    @Override
//    @SuppressWarnings("ConstantConditions")
//    protected Single<List<Posting>> retrieve() {
//        return RetrofitService.getInstance()
//                .create(InformationApi.class)
//                .getPosting("Berita")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .flatMap(response -> {
//                    RootResponse<List<Posting>> rootResponse = response.body();
//                    if (response.code() == 200) {
//                        setCache(rootResponse.getResult());
//                    }
//
//                    return Single.just(rootResponse.getResult());
//                });
//    }

    @Override
    protected Single<List<Event>> retrieve() {
        return RetrofitService.getInstance()
                .create(EventApi.class)
                .getEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Event>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }

                    return Single.just(rootResponse.getResult());
                });
    }


}
