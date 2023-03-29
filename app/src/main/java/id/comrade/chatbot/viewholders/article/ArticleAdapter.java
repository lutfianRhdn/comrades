package id.comrade.chatbot.viewholders.article;

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
import id.comrade.article.ArticleActivity;
import id.comrade.model.Article;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.VH> {
    private Context mContext;

    private List<Article> mArticle;

    ArticleAdapter(Context ctx, List<Article> article) {
        mArticle = article;
        mContext = ctx;
    }

    @NonNull
    @Override
    public ArticleAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleAdapter.VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatbot_list_news, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.VH holder, int position) {
        Article article = mArticle.get(position);

        holder.tvTitle.setText(article.getJudul());
        holder.tvSource.setText(article.getPengirim());
        holder.tvTime.setText(DateUtils.getDifference(mContext, article.getTglPosting()));
        holder.sdvHeadline.setImageURI(RetrofitService.PIC_POSTING + article.getFoto());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ArticleActivity.class)
                    .putExtra(ArticleActivity.ARTICLE_EXTRA, article);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mArticle != null ? mArticle.size() : 0;
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
