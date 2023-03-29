package id.comrade.chatbot.viewholders.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.model.Posting;
import id.comrade.news.NewsActivity;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    private Context mContext;

    private List<Posting> mNews;

    NewsAdapter(Context ctx, List<Posting> news) {
        mNews = news;
        mContext = ctx;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatbot_list_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Posting news = mNews.get(position);

        holder.tvTitle.setText(news.getJudul());
        holder.tvSource.setText(news.getPengirim());
        holder.tvTime.setText(DateUtils.getDifference(mContext, news.getTanggalTerbit()));
        holder.sdvHeadline.setImageURI(RetrofitService.PIC_POSTING + news.getFoto());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NewsActivity.class)
                    .putExtra(NewsActivity.NEWS_EXTRA, news);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mNews != null ? mNews.size() : 0;
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_chatbot_list_news_header)
        SimpleDraweeView sdvHeadline;

        @BindView(R.id.tv_item_chatbot_list_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_chatbot_list_news_time)
        TextView tvTime;
        @BindView(R.id.tv_item_chatbot_list_news_source)
        TextView tvSource;

        VH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
