package id.comrade.article;

import android.app.Application;

import id.comrade.base.BaseViewModel;

public class ArticleViewModel extends BaseViewModel<ArticleViewState> {
    public ArticleViewModel(Application app) {
        super(app);

        ArticleViewState viewState = new ArticleViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    void finishLoad() {
        ArticleViewState viewState = getViewState().getValue();
        viewState.setLoading(false);
        getViewState().setValue(viewState);
    }
}
