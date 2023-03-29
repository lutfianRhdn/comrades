package id.comrade.repositories;

import java.util.List;
import java.util.Map;

import id.comrade.model.Chat;
import id.comrade.services.webservice.KonsultasiApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatCache extends Cache<Chat> {

    private static ChatCache sInstance;

    private KonsultasiApi mApi;

    private Map<String, String> map;

    private ChatCache() {
        mApi = RetrofitService.getInstance().create(KonsultasiApi.class);
    }

    public static ChatCache getInstance(Map<String, String> map) {
        if (sInstance == null) {
            sInstance = new ChatCache();
        }

        sInstance.setMap(map);
        return sInstance;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Single<List<Chat>> retrieve() {
        return null;
//        return mApi.getChats(map)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .flatMap(response -> Single.just(response.body().getResult()));
    }

    public void send(Map<String, String> chat) {
        mApi.sendChat(chat)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()
                .dispose();
    }

    private void setMap(Map<String, String> map) {
        this.map = map;
    }
}
