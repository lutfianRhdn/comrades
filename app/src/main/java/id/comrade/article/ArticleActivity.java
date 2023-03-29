package id.comrade.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.model.Article;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class ArticleActivity extends BaseActivity<ArticleViewState, ArticleViewModel> {
    public static String ARTICLE_EXTRA = "intent-extra:article";
    public static String TAG = "cek";

    @BindView(R.id.ll_activity_news_main_view)
    LinearLayout llMainView;

    @BindView(R.id.pb_activity_news_progress)
    ProgressBar pbProgress;

    @BindView(R.id.tv_activity_news_judul)
    TextView tvTitle;
    @BindView(R.id.tv_activity_news_date)
    TextView tvDate;
    @BindView(R.id.tv_activity_news_komunitas)
    TextView tvKomunitas;

    @BindView(R.id.toolbar_activity_news)
    Toolbar toolbar;

    @BindView(R.id.sdv_activity_news_image)
    SimpleDraweeView sdvImage;

    @BindView(R.id.wv_activity_news_content)
    WebView wbContent;

    private Article mArticle;

    private WebViewClient webViewClient;

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(ArticleViewModel.class);

        webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                getViewModel().finishLoad();
            }
        };

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mArticle = intent.getParcelableExtra(ARTICLE_EXTRA);
            wbContent.loadData(mArticle.getIsi(), "text/html", null);
            wbContent.setWebViewClient(webViewClient);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewClient = null;
    }

    private void initView() {
        tvTitle.setText(mArticle.getJudul());
        tvDate.setText(DateUtils.format(mArticle.getTglPosting()));
        tvKomunitas.setText(mArticle.getPengirim());
        sdvImage.setImageURI(RetrofitService.PIC_POSTING + mArticle.getFoto());

    }

    @Override
    protected void handleViewState(ArticleViewState viewState) {
        handleLoading(viewState.isLoading());
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.keyword_berita);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected int getContentView() {

        return R.layout.activity_news;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    private void handleLoading(boolean loading) {
        if (loading) {
            pbProgress.setVisibility(View.VISIBLE);
            llMainView.setVisibility(View.GONE);
        } else {
            pbProgress.setVisibility(View.GONE);
            llMainView.setVisibility(View.VISIBLE);
            initView();
        }
    }
}
