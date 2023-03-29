package id.comrade.news.latest;

import android.app.Application;

import id.comrade.base.BaseViewModel;
import id.comrade.repositories.NewsCache;

public class LatestNewsViewModel extends BaseViewModel<LatestNewsViewState> {
    private NewsCache newsCache = NewsCache.getInstance();

    public LatestNewsViewModel(Application app) {
        super(app);
        LatestNewsViewState viewState = new LatestNewsViewState();
        getViewState().setValue(viewState);

        addDisposable(newsCache.getCache()
            .subscribe(news -> {
                  viewState.setNews(news);
                  getViewState().setValue(viewState);
            }));
    }
}
