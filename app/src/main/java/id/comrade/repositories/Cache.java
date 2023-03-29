package id.comrade.repositories;


import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class Cache<T> {
    private List<T> cache;

    public Single<List<T>> getCache() {
        if (cache == null) {
            return retrieve();
        }

        return Single.fromCallable(() -> cache)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public void addCache(T item) {
        cache.add(item);
    }

    public void removeCache(T item) {
        cache.remove(item);
    }

    void setCache(List<T> cache) {
        this.cache = cache;
    }

    protected abstract Single<List<T>> retrieve();
}
