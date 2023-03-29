package id.comrade.groupchats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.comrade.R;
import id.comrade.model.Chat;

public class GroupChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_SEND = 0;
    private static final int MESSAGE_RECEIVED = 1;

    private ArrayList<Chat> mChats = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case MESSAGE_SEND:
                return new GroupChatAdapter.ViewHolder(inflater.inflate(R.layout.item_chat_message_sent, parent,
                        false), R.id.tv_item_chat_message_sent);
            default:
                return new GroupChatAdapter.ViewHolder(inflater.inflate(R.layout.item_chat_message_received, parent,
                        false), R.id.tv_item_chat_message_received);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupChatAdapter.ViewHolder vh = (ViewHolder) holder;
        vh.tvChat.setText(mChats.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public void setChats(ArrayList<Chat> chats) {
        mChats = chats;
        notifyDataSetChanged();
    }

    public void addChat(Chat chat) {
        mChats.add(chat);
        notifyDataSetChanged();
    }

    public List<Chat> getChats() {
        return mChats;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChat;

        ViewHolder(View view, int tvId) {
            super(view);
            tvChat = view.findViewById(tvId);
        }
    }
}
