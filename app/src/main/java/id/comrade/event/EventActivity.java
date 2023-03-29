package id.comrade.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import id.comrade.model.Event;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class EventActivity extends BaseActivity<EventViewState, EventViewModel> {
    public static String EVENT_EXTRA = "intent-extra:event";

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

    private Event mEvent;

    private WebViewClient webViewClient;

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(EventViewModel.class);

        webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                getViewModel().finishLoad();
            }
        };

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mEvent = intent.getParcelableExtra(EVENT_EXTRA);
            wbContent.loadData(mEvent.getDeskripsi(), "text/html", null);
            wbContent.setWebViewClient(webViewClient);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewClient = null;
    }

    private void initView() {
        Log.d("cek", "initView: "+mEvent.getNama());
        tvTitle.setText(mEvent.getNama());
        tvDate.setText(DateUtils.format(mEvent.getTglPosting()));
        tvKomunitas.setText(mEvent.getPengirim());
        sdvImage.setImageURI(RetrofitService.PIC_EVENT + mEvent.getFoto());
    }

    @Override
    protected void handleViewState(EventViewState viewState) {
        handleLoading(viewState.isLoading());
    }

    @Override
    protected String getToolbarTitle() {
        return "event";
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

//    @BindView(R.id.toolbar_activity_latest_news)
//    Toolbar toolbar;
//
//    @BindView(R.id.rv_activity_latest_news)
//    RecyclerView rvNews;
//
//    private EventAdapter adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        init(EventViewModel.class);
//
//        adapter = new EventAdapter(this);
//        rvNews.setAdapter(adapter);
//        Log.d("cek", "Di LatestNews");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        adapter = null;
//    }
//
//    @Override
//    protected void handleViewState(EventViewState viewState) {
//        handleNews(viewState.getEvents());
//    }
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_latest_news;
//    }
//
//    @Override
//    protected Toolbar getToolbar() {
//        return toolbar;
//    }
//
//    @Override
//    protected String getToolbarTitle() {
//        return getString(R.string.label_comrades_news);
//    }
//
//    @Override
//    protected boolean hasParent() {
//        return true;
//    }
//
//    private void handleNews(List<Event> events) {
//        if (events != null) {
//            adapter.setEvent(events);
//        }
//    }
}
