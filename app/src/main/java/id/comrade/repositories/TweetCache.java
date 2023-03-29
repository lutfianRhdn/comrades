package id.comrade.repositories;

import android.util.Log;

import java.util.List;

import id.comrade.model.RootResponse;
import id.comrade.model.Tweet;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.TweetApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TweetCache extends Cache<Tweet>{
    private static TweetCache instance;

    public static TweetCache getInstance(){
        if (instance == null){
            instance = new TweetCache();
        }
        return instance;
    }
    @Override
    protected Single<List<Tweet>> retrieve() {
        return RetrofitService.getInstance()
                .create(TweetApi.class)
                .getTweet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Tweet>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }
                    Log.d("cek", rootResponse.getResult().get(0).getProfileImageUrl());

                    return Single.just(rootResponse.getResult());
                });
    }
}
