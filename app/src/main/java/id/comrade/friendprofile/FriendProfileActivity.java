package id.comrade.friendprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.konsultasi.KonsultasiActivity;
import id.comrade.model.SahabatOdha;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;

public class FriendProfileActivity extends BaseActivity<FriendProfileViewState,
        FriendProfileViewModel> {
    public static final String USER_EXTRA = "intent-extra:user";

    @BindView(R.id.toolbar_activity_friend_photo)
    Toolbar toolbar;

    @BindView(R.id.sdv_activity_friend_profile_photo)
    SimpleDraweeView sdvFriendPhoto;

    @BindView(R.id.tv_activity_friend_profile_name_photo)
    TextView tvFriendName;
    @BindView(R.id.tv_activity_friend_profile_city)
    TextView tvFriendCity;
    @BindView(R.id.tv_activity_friend_profile_community_name)
    TextView tvFriendCommunity;
    @BindView(R.id.tv_activity_friend_profile_biography)
    TextView tvFriendBiography;

    @BindView(R.id.btn_activity_friend_profile_start_chatting)
    Button btnStartChat;

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(FriendProfileViewModel.class);

        getViewModel().setUser(getIntent().getParcelableExtra(USER_EXTRA));
    }

    @Override
    protected void handleViewState(FriendProfileViewState viewState) {
        handleUser(viewState.getUser());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_friend_profile);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    private void handleUser(User user) {
        if (user == null) {
            return;
        }

        SahabatOdha sahabatOdha = user.getSahabatOdha();

        tvFriendName.setText(user.getNama());

        try {
            tvFriendCommunity.setText(sahabatOdha.getKomunitas());
            tvFriendBiography.setText(sahabatOdha.getAbout());
            tvFriendCity.setText(sahabatOdha.getKota());
        } catch (Exception e){
            e.getLocalizedMessage();
        }

        sdvFriendPhoto.setImageURI(RetrofitService.PIC_USER + user.getFoto());
        btnStartChat.setOnClickListener(v -> {
            if (user.getTokenFcm() != null){
                Intent intent = new Intent(this, KonsultasiActivity.class)
                        .putExtra(KonsultasiActivity.USER_EXTRA, user);
                startActivity(intent);
            } else {
                showToast("Sahabat ODHA belum melakukan update aplikasi, untuk sementara tidak bisa dichat.");
            }

        });
    }
}
