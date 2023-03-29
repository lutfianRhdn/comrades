package id.comrade.chatbot;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.comrade.R;
import id.comrade.chatbot.viewholders.MessageViewHolder;
import id.comrade.chatbot.viewholders.article.ArticleViewHolder;
import id.comrade.chatbot.viewholders.event.EventViewHolder;
import id.comrade.chatbot.viewholders.headings.HeadingViewHolder;
import id.comrade.chatbot.viewholders.news.NewsViewHolder;
import id.comrade.chatbot.viewholders.SharingFriendViewHolder;
import id.comrade.chatbot.viewholders.tweet.TweetViewHolder;
import id.comrade.model.Chat;
import id.comrade.model.ChatArticle;
import id.comrade.model.ChatEvent;
import id.comrade.model.ChatNews;
import id.comrade.model.ChatSharingFriend;
import id.comrade.model.ChatTweet;

public class ChatbotAdapter extends RecyclerView.Adapter<ChatbotAdapter.ViewHolder> {
    public static final String CHATBOT_NAME = "Cora";
    public static final String CHATBOT_SEND_TO = "User";

    private static final int MESSAGE_RECEIVED_VIEW_TYPE = 1;
    private static final int NEWS_VIEW_TYPE = 2;
    private static final int SHARING_FRIEND_VIEW_TYPE = 3;
    private static final int MESSAGE_SENT_VIEW_TYPE = 4;
    private static final int HEADING_VIEW_TYPE = 5;
    private static final int EVENT_VIEW_TYPE = 6;
    private static final int ARTICLE_VIEW_TYPE = 7;
    private static final int TWEET_VIEW_TYPE = 8;

    private static final int DEFAULT_SIZE = 1;

    private Activity activity;

    private ArrayList<Chat> chats = new ArrayList<>();

    ChatbotAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADING_VIEW_TYPE;
        }

        Chat chat = chats.get(position - DEFAULT_SIZE);

        if (chat instanceof ChatNews) {
            return NEWS_VIEW_TYPE;
        } else if (chat instanceof ChatSharingFriend) {
            return SHARING_FRIEND_VIEW_TYPE;
        } else if (chat instanceof ChatEvent){
            return EVENT_VIEW_TYPE;
        } else if(chat instanceof ChatArticle){
            return ARTICLE_VIEW_TYPE;
        } else if(chat instanceof ChatTweet){
            return TWEET_VIEW_TYPE;
        }else if (chat.getUidSender().equals(CHATBOT_NAME)) {
            return MESSAGE_RECEIVED_VIEW_TYPE;
        } else {
            return MESSAGE_SENT_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = position == 0 ? null : chats.get(position - DEFAULT_SIZE);
        holder.init(chat);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADING_VIEW_TYPE:
                return new HeadingViewHolder(inflater.inflate(
                        R.layout.item_chatbot_heading, parent, false), activity);
            case NEWS_VIEW_TYPE:
                return new NewsViewHolder(inflater.inflate(
                        R.layout.item_chatbot_with_list, parent, false), activity);
            case SHARING_FRIEND_VIEW_TYPE:
                return new SharingFriendViewHolder(inflater.inflate(
                        R.layout.item_chatbot_with_list, parent, false), activity);
            case MESSAGE_RECEIVED_VIEW_TYPE:
                return new MessageViewHolder(inflater.inflate(
                        R.layout.item_chat_message_received, parent, false), activity,
                        R.id.tv_item_chat_message_received);
            case EVENT_VIEW_TYPE:
                return new EventViewHolder(inflater.inflate(
                        R.layout.item_chatbot_with_list, parent, false), activity);
            case ARTICLE_VIEW_TYPE:
                return new ArticleViewHolder(inflater.inflate(
                        R.layout.item_chatbot_with_list,parent,false), activity);
            case TWEET_VIEW_TYPE:
                return new TweetViewHolder(inflater.inflate(
                        R.layout.item_chatbot_with_list,parent,false),activity);
            default:
                return new MessageViewHolder(inflater.inflate(
                        R.layout.item_chat_message_sent, parent, false), activity,
                        R.id.tv_item_chat_message_sent);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size() + DEFAULT_SIZE;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
        notifyItemInserted(chats.size());
    }

    public List<Chat> getChats() {
        return chats;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        private Activity mActivity;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;
        }

        protected Activity getActivity() {
            return mActivity;
        }

        public abstract void init(Chat chat);
    }
}
