package id.comrade.news;

import android.app.Application;

import id.comrade.base.BaseViewModel;

public class NewsViewModel extends BaseViewModel<NewsViewState> {
    public NewsViewModel(Application app) {
        super(app);

        NewsViewState viewState = new NewsViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    void finishLoad() {
        NewsViewState viewState = getViewState().getValue();
        viewState.setLoading(false);
        getViewState().setValue(viewState);
    }
}
