package id.comrade.news.latest;

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

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder> {
    private static final int HEADING_VIEW = 0;
    private static final int LIST_VIEW = 1;

    private Context context;

    private List<Posting> news;

    public LatestNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADING_VIEW : LIST_VIEW;
    }

    @NonNull
    @Override
    public LatestNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADING_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_latest_news_heading, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_latest_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LatestNewsAdapter.ViewHolder holder, int position) {
        Posting news = this.news.get(position);

        holder.sdvImage.setImageURI(RetrofitService.PIC_POSTING + news.getFoto());
        holder.tvSource.setText(news.getPengirim());
        holder.tvTitle.setText(news.getJudul());
        holder.tvTime.setText(DateUtils.getDifference(context, news.getTanggalTerbit()));
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
        @BindView(R.id.sdv_item_latest_news_image)
        SimpleDraweeView sdvImage;

        @BindView(R.id.tv_item_latest_news_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_latest_news_time)
        TextView tvTime;
        @BindView(R.id.tv_item_latest_news_source)
        TextView tvSource;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
