package id.comrade.chatbot.viewholders.news;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.model.Chat;
import id.comrade.model.ChatNews;
import id.comrade.model.Posting;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class NewsViewHolder extends ChatbotAdapter.ViewHolder {
    @BindView(R.id.rv_item_chatbot_with_list)
    RecyclerView rvNews;

    @BindView(R.id.tv_item_chatbot_with_list_message)
    TextView tvMessage;

    public NewsViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void init(Chat chat) {
        ChatNews chatNews = (ChatNews) chat;

        tvMessage.setText(chatNews.getMessage());
        rvNews.setAdapter(new NewsAdapter(getActivity(), chatNews.getNews()));
    }
}
