package id.comrade.home;

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

class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> {
    private Context context;

    private List<Posting> news;

    HomeNewsAdapter(Context ctx) {
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatbot_list_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting news = this.news.get(position);

        holder.tvTitle.setText(news.getJudul());
        holder.tvSource.setText(news.getPengirim());
        holder.tvTime.setText(DateUtils.getDifference(context, news.getTanggalTerbit()));
        holder.sdvHeadline.setImageURI(RetrofitService.PIC_POSTING + news.getFoto());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsActivity.class)
                    .putExtra(NewsActivity.NEWS_EXTRA, news);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    public void setNews(List<Posting> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_chatbot_list_news_header)
        SimpleDraweeView sdvHeadline;

        @BindView(R.id.tv_item_chatbot_list_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_chatbot_list_news_time)
        TextView tvTime;
        @BindView(R.id.tv_item_chatbot_list_news_source)
        TextView tvSource;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
