package id.comrade.chatbot.viewholders.event;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.event.EventActivity;
import id.comrade.model.Event;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.VH>{

    private Context mContext;

    private List<Event> mEvent;

    EventAdapter(Context ctx, List<Event> event) {
        mEvent = event;
        mContext = ctx;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatbot_list_news, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Event event = mEvent.get(position);

        holder.tvTitle.setText(event.getNama());
        holder.tvSource.setText(event.getPengirim());
        holder.tvTime.setText(DateUtils.getDifference(mContext, event.getTglPosting() ));
        holder.sdvHeadline.setImageURI(RetrofitService.PIC_EVENT + event.getFoto());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EventActivity.class)
                    .putExtra(EventActivity.EVENT_EXTRA, event);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mEvent != null ? mEvent.size() : 0;
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_chatbot_list_news_header)
        SimpleDraweeView sdvHeadline;

        @BindView(R.id.tv_item_chatbot_list_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_chatbot_list_news_time)
        TextView tvTime;
        @BindView(R.id.tv_item_chatbot_list_news_source)
        TextView tvSource;

        VH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
