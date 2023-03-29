package id.comrade.event;

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
import id.comrade.model.Event;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private static final int HEADING_VIEW = 0;
    private static final int LIST_VIEW = 1;

    private Context context;

    private List<Event> event;

    public EventAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADING_VIEW : LIST_VIEW;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADING_VIEW) {
            return new EventAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_latest_news_heading, parent, false));
        } else {
            return new EventAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_latest_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = this.event.get(position);

        holder.sdvImage.setImageURI(RetrofitService.PIC_EVENT + event.getFoto());
        holder.tvSource.setText(event.getPengirim());
        holder.tvTitle.setText(event.getNama());
        holder.tvTime.setText(DateUtils.getDifference(context, event.getTglPosting()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventActivity.class)
                    .putExtra(EventActivity.EVENT_EXTRA, event);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return event == null ? 0 : event.size();
    }

    public void setEvent(List<Event> event) {
        this.event = event;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_latest_news_image)
        SimpleDraweeView sdvImage;

        @BindView(R.id.tv_item_latest_news_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_latest_news_time)
        TextView tvTime;
        @BindView(R.id.tv_item_latest_news_source)
        TextView tvSource;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
