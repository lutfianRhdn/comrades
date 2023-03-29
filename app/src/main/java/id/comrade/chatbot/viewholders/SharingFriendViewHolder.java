package id.comrade.chatbot.viewholders;

import android.app.Activity;
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
import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.friendprofile.FriendProfileActivity;
import id.comrade.model.Chat;
import id.comrade.model.ChatSharingFriend;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;

public class SharingFriendViewHolder extends ChatbotAdapter.ViewHolder {
    @BindView(R.id.rv_item_chatbot_with_list)
    RecyclerView rvSharingFriends;

    @BindView(R.id.tv_item_chatbot_with_list_message)
    TextView tvMessage;

    private List<User> mFriends;

    public SharingFriendViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void init(Chat chat) {
        ChatSharingFriend chatSharingFriend = (ChatSharingFriend) chat;

        tvMessage.setText(chatSharingFriend.getMessage());
        mFriends = chatSharingFriend.getFriends();

        rvSharingFriends.setAdapter(new SharingFriendAdapter());
    }

    private class SharingFriendAdapter extends RecyclerView.Adapter<VH> {
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chatbot_list_friend_to_share, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            User friend = mFriends.get(position);
            holder.tvName.setText(friend.getNama());
            holder.sdvProfPic.setImageURI(RetrofitService.PIC_USER + friend.getFoto());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), FriendProfileActivity.class)
                        .putExtra(FriendProfileActivity.USER_EXTRA, friend);

                getActivity().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_chatbot_list_friend_to_share_profile_picture)
        SimpleDraweeView sdvProfPic;

        @BindView(R.id.tv_item_chatbot_list_friend_to_share_name)
        TextView tvName;

        VH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
