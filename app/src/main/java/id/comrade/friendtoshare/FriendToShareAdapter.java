package id.comrade.friendtoshare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.friendprofile.FriendProfileActivity;
import id.comrade.model.Rating;
import id.comrade.model.SahabatOdha;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.CurrencyFormatter;

public class FriendToShareAdapter extends RecyclerView.Adapter<FriendToShareAdapter.ViewHolder> {
    private Context context;

    private List<User> friends;

    FriendToShareAdapter(Context ctx) {
        context = ctx;
    }

    @NonNull
    @Override
    public FriendToShareAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_to_share, parent, false));
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FriendToShareAdapter.ViewHolder holder, int position) {
        User friend = friends.get(position);
        SahabatOdha sahabatOdha = friend.getSahabatOdha();

        if (sahabatOdha != null) {
            holder.tvFriendLocation.setText(sahabatOdha.getKomunitas());
            holder.tvFriendPrice.setText(context.getString(R.string.label_price_per_session,
                    CurrencyFormatter.format(sahabatOdha.getHarga())));

            Rating[] ratingSahabatOdha = friend.getRatings();
            if (ratingSahabatOdha.length > 0) {
                String rating = String.valueOf(ratingSahabatOdha[0].getRating());
                rating = rating.length() > 4 ? rating.substring(0, 4) : rating;
                holder.tvFriendRating.setText(rating);
            } else {
                holder.tvFriendRating.setText("0");
            }
        }

        holder.sdvFriendPicture.setImageURI(RetrofitService.PIC_USER + friend.getFoto());
        holder.tvFriendName.setText(friend.getNama());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FriendProfileActivity.class)
                    .putExtra(FriendProfileActivity.USER_EXTRA, friend);
            context.startActivity(intent);
        });
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_friend_to_share_photo)
        SimpleDraweeView sdvFriendPicture;

        @BindView(R.id.tv_item_friend_to_share_name)
        TextView tvFriendName;
        @BindView(R.id.tv_item_friend_to_share_location)
        TextView tvFriendLocation;
        @BindView(R.id.tv_item_friend_to_share_price)
        TextView tvFriendPrice;
        @BindView(R.id.tv_item_friend_to_share_rating)
        TextView tvFriendRating;

        @BindView(R.id.iv_item_friend_to_share_online_status)
        ImageView ivOnlineStatus;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}
