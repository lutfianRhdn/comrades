package id.comrade.news.latest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.model.Posting;

public class LatestNewsActivity extends BaseActivity<LatestNewsViewState, LatestNewsViewModel> {

    @BindView(R.id.toolbar_activity_latest_news)
    Toolbar toolbar;

    @BindView(R.id.rv_activity_latest_news)
    RecyclerView rvNews;

    private LatestNewsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(LatestNewsViewModel.class);

        adapter = new LatestNewsAdapter(this);
        rvNews.setAdapter(adapter);
        Log.d("cek", "Di LatestNews");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Override
    protected void handleViewState(LatestNewsViewState viewState) {
        handleNews(viewState.getNews());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_latest_news;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_comrades_news);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    private void handleNews(List<Posting> news) {
        if (news != null) {
            adapter.setNews(news);
        }
    }
}
