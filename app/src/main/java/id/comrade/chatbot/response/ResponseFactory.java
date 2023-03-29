package id.comrade.chatbot.response;

import android.content.Context;

import java.util.HashMap;

import id.comrade.R;
import id.comrade.model.Chat;
import id.comrade.model.ChatArticle;
import id.comrade.model.ChatEvent;
import id.comrade.model.ChatNews;
import id.comrade.model.ChatSharingFriend;
import id.comrade.model.ChatTweet;
import id.comrade.utils.DateUtils;

import static id.comrade.chatbot.ChatbotAdapter.CHATBOT_NAME;
import static id.comrade.chatbot.ChatbotAdapter.CHATBOT_SEND_TO;

public class ResponseFactory {

    private HashMap<String, Chat> mResponse;

    public ResponseFactory(Context ctx) {
        mResponse = new HashMap<String, Chat>() {{
            put(ctx.getString(R.string.keyword_berita), new ChatNews(
                    ctx.getString(R.string.response_berita), DateUtils.getDate(),
                    DateUtils.getTime(), Chat.READ, CHATBOT_NAME, CHATBOT_SEND_TO, null));

            put(ctx.getString(R.string.keyword_konsultasi), new ChatSharingFriend(
                    ctx.getString(R.string.response_konsultasi), DateUtils.getDate(),
                    DateUtils.getTime(), Chat.READ, CHATBOT_NAME, CHATBOT_SEND_TO, null));

            put(ctx.getString(R.string.keyword_group_chat), new Chat(
                    ctx.getString(R.string.response_group_chat), DateUtils.getDate(),
                    DateUtils.getTime(), Chat.READ, CHATBOT_NAME, CHATBOT_SEND_TO));

            put(ctx.getString(R.string.keyword_event),new ChatEvent(
                    ctx.getString(R.string.response_event),DateUtils.getDate(),
                    DateUtils.getTime(),Chat.READ,CHATBOT_NAME,CHATBOT_SEND_TO,null));

            put(ctx.getString(R.string.keyword_artikel),new ChatArticle(
                    ctx.getString(R.string.response_artikel),DateUtils.getDate(),
                    DateUtils.getTime(),Chat.READ,CHATBOT_NAME,CHATBOT_SEND_TO,null));
            put(ctx.getString(R.string.keyword_tweet),new ChatTweet(
                    ctx.getString(R.string.response_tweet), DateUtils.getDate(),
                    DateUtils.getTime(), Chat.READ,CHATBOT_NAME,CHATBOT_SEND_TO,null));
        }};
    }

    public Chat get(String msg) {
        return mResponse.get(msg);
    }
}
