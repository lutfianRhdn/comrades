package id.comrade.chatbot.viewholders.headings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.comrade.R;
import id.comrade.model.Topic;

public class HeadingTopicAdapter extends RecyclerView.Adapter<HeadingTopicAdapter.ViewHolder> {

    private static final int MAX = 6;

    private List<Topic> mTopics;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String topic = "";
        try{
            topic = mTopics.get(position).getTopik();
        }catch(Exception e){
            topic= "null";
        }
        holder.tvSuggestion.setText(topic);

    }

    @Override
    public int getItemCount() {
        return mTopics != null ? MAX : 0;
    }

    public void setTopics(List<Topic> topics) {
        mTopics = topics;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSuggestion;

        ViewHolder(View view) {
            super(view);
            tvSuggestion = view.findViewById(R.id.tv_item_home_suggestion);
        }
    }
}
