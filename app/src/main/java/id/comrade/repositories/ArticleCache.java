package id.comrade.repositories;

import java.util.List;

import id.comrade.model.Article;
import id.comrade.model.RootResponse;
import id.comrade.services.webservice.ArticleApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleCache extends Cache<Article> {
    private static ArticleCache instance;

    public static ArticleCache getInstance(){
        if (instance == null){
            instance = new ArticleCache();
        }
        return instance;
    }

    protected Single<List<Article>> retrieve() {
        return RetrofitService.getInstance()
                .create(ArticleApi.class)
                .getPosting("Artikel")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    RootResponse<List<Article>> rootResponse = response.body();
                    if (response.code() == 200) {
                        setCache(rootResponse.getResult());
                    }
                    return Single.just(rootResponse.getResult());
                });
    }
}
