package id.comrade.chatbot.viewholders.tweet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.model.Tweet;
import id.comrade.utils.DateUtils;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.VH>{
    private Context mContext;

    private List<Tweet> mTweet;

    TweetAdapter(Context ctx, List<Tweet> tweet) {
        mTweet = tweet;
        mContext = ctx;
    }

    @NonNull
    @Override
    public TweetAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TweetAdapter.VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatbot_list_tweet, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull TweetAdapter.VH holder, int position) {
        Tweet tweet = mTweet.get(position);

        holder.tvTitle.setText(tweet.getScreenName());
        holder.tvSource.setText(tweet.getText());
        holder.tvTime.setText(DateUtils.getDifference(mContext, tweet.getCreatedAt() ));
        holder.sdvHeadline.setImageURI(tweet.getProfileImageUrl());
//        holder.card_relative.setBackground(new TweetDrawable("#"+tweet.getProfileLinkColor()));
//        holder.card_relative.setBackgroundColor(Color.parseColor());

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, EventActivity.class)
//                    .putExtra(EventActivity.EVENT_EXTRA, tweet);
//            mContext.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return mTweet != null ? mTweet.size() : 0;
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
        @BindView(R.id.card_relative)
        RelativeLayout card_relative;


        VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
