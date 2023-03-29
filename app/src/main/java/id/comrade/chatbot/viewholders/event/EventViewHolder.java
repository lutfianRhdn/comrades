package id.comrade.chatbot.viewholders.event;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.model.Chat;
import id.comrade.model.ChatEvent;

public class EventViewHolder extends ChatbotAdapter.ViewHolder{
    @BindView(R.id.rv_item_chatbot_with_list)
    RecyclerView rvNews;

    @BindView(R.id.tv_item_chatbot_with_list_message)
    TextView tvMessage;

    public EventViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void init(Chat chat) {
        ChatEvent chatEvent = (ChatEvent) chat;

        tvMessage.setText(chatEvent.getMessage());
        rvNews.setAdapter(new EventAdapter(getActivity(), chatEvent.getEvent()));
    }
}
