package id.comrade.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.comrade.R;
import id.comrade.chatbot.ChatbotActivity;
import id.comrade.model.Topic;

public class HomeTopicAdapter extends RecyclerView.Adapter<HomeTopicAdapter.ViewHolder> {

    private static final int MAX = 6;

    private Context mContext;

    private List<Topic> mTopics;

    HomeTopicAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Log.d(String.valueOf(mTopics.size()), "onBindViewHolder: postion and size");
            Log.d(String.valueOf(position), "onBindViewHolder: pisition");
            String topik ="";
            try{
                topik = mTopics.get(position).getTopik();

            }catch(Exception e){
                topik ="null";
            }

        holder.tvSuggestion.setText(topik);
        String finalTopik = topik;
        holder.tvSuggestion.setOnClickListener(v -> chatCora(finalTopik));

    }

    @Override
    public int getItemCount() {
        return mTopics != null ? MAX : 0;
    }

    public void setTopics(List<Topic> topics) {
        mTopics = topics;
        notifyDataSetChanged();
    }

    private void chatCora(String message) {
        Intent intent = new Intent(mContext, ChatbotActivity.class)
                .putExtra(ChatbotActivity.MESSAGE_EXTRA, message);
        mContext.startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSuggestion;

        ViewHolder(View view) {
            super(view);
            tvSuggestion = view.findViewById(R.id.tv_item_home_suggestion);
        }
    }
}
