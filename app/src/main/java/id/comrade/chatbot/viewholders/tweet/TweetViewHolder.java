package id.comrade.chatbot.viewholders.tweet;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.model.Chat;
import id.comrade.model.ChatTweet;

public class TweetViewHolder extends ChatbotAdapter.ViewHolder {
    @BindView(R.id.rv_item_chatbot_with_list)
    RecyclerView rvNews;

    @BindView(R.id.tv_item_chatbot_with_list_message)
    TextView tvMessage;

    public TweetViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void init(Chat chat) {
        ChatTweet chatTweet = (ChatTweet) chat;

        tvMessage.setText(chatTweet.getMessage());
        rvNews.setAdapter(new TweetAdapter(getActivity(), chatTweet.getTweet()));
    }
}
