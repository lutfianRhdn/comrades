package id.comrade.konsultasi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.comrade.R;
import id.comrade.model.Konsultasi;
import id.comrade.utils.DateUtils;

public class KonsultasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_SEND = 0;
    private static final int MESSAGE_RECEIVED = 1;

    public static final String ODHA = "odha";

    private List<Konsultasi> mChats = new ArrayList<>();
    private String fcmSender;

    private int numReply = 0;

    public KonsultasiAdapter(String fcmSender) {
        this.fcmSender = fcmSender;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case MESSAGE_SEND:
                return new ViewHolder(inflater.inflate(R.layout.item_chat_message_sent, parent,
                        false), R.id.tv_item_chat_message_sent);
//                        false), R.id.tv_item_chat_message_sent);
            default:
                return new ViewHolder(inflater.inflate(R.layout.item_chat_message_received, parent,
                        false), R.id.tv_item_chat_message_received);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.tvChat.setText(mChats.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        return mChats.get(position).getRegistrationTokenFrom().equals(fcmSender) ?
                MESSAGE_SEND : MESSAGE_RECEIVED;
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public void setChats(List<Konsultasi> chats) {
        mChats = chats;
        notifyDataSetChanged();
    }

    public void addChat(Konsultasi chat, AppCompatActivity activity) {
        mChats.add(chat);
        notifyDataSetChanged();
    }

    public List<Konsultasi> getChats() {
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
