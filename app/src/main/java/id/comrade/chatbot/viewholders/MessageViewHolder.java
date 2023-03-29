package id.comrade.chatbot.viewholders;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.model.Chat;

public class MessageViewHolder extends ChatbotAdapter.ViewHolder {

    private TextView tvChat;

    public MessageViewHolder(View itemView, Activity activity, @IdRes int textViewId) {
        super(itemView, activity);
        tvChat = itemView.findViewById(textViewId);
    }

    @Override
    public void init(Chat chat) {
        tvChat.setText(chat.getMessage());
    }
}
