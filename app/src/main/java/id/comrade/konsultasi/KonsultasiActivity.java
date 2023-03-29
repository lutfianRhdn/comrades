package id.comrade.konsultasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.model.Konsultasi;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.utils.DateUtils;
import id.comrade.utils.SharedPreferenceUtil;

public class KonsultasiActivity extends BaseActivity<KonsultasiViewState, KonsultasiViewModel> {
    public static final String USER_EXTRA = "intent-extra:user";

    private List<Konsultasi> konsultasis;
    private Thread thread = new Thread();

    @BindView(R.id.rv_activity_konsultasi)
    RecyclerView rvChats;

    @BindView(R.id.iv_activity_konsultasi_send)
    ImageView ivSend;

    @BindView(R.id.tv_activity_konsultasi_send)
    TextView tvSend;

    @BindView(R.id.et_activity_konsultasi_chatbox)
    EditText etChatbox;

    @BindView(R.id.toolbar_activity_konsultasi)
    Toolbar toolbar;

    private KonsultasiAdapter adapter;

    private ToolbarViewHolder toolbarViewHolder;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(KonsultasiViewModel.class);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            user = intent.getParcelableExtra(USER_EXTRA);
        }

        toolbarViewHolder = new ToolbarViewHolder(toolbar) {{
            sdvFriendPicture.setImageURI(RetrofitService.PIC_USER + user.getFoto());
            tvFriendName.setText(user.getNama());
            tvFriendLastSeen.setText(getString(R.string.last_seen_label, "19:45"));
        }};

        konsultasis = new ArrayList<>();
        adapter = new KonsultasiAdapter(getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN));
        rvChats.setAdapter(adapter);
        ivSend.setOnClickListener(v -> showToast("Coming soon! Please be patient ~"));
        tvSend.setOnClickListener(v -> sendChat());

        Log.i("ceks2",getSharedPreferences(User.PREF_USER_ID));
        Log.i("ceks",String.valueOf(user.getIdUser()));

        Map<String, String> body = new HashMap<String, String>() {{
            put("user1", String.valueOf(user.getIdUser()));
            put("user2", getSharedPreferences(User.PREF_USER_ID));
        }};
        getViewModel().getChat(body);
        check_chat_every_time(body);

    }

        private void check_chat_every_time(Map<String,String> body){
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getViewModel().getChat(body);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        toolbarViewHolder = null;
    }

    @Override
    protected void handleViewState(KonsultasiViewState viewState) {
        for (Konsultasi konsultasi : viewState.getKonsultasis()) {
            System.out.println(konsultasi.getMessage());
        }

        adapter.setChats(viewState.getKonsultasis());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_konsultasi;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @OnTextChanged(R.id.et_activity_konsultasi_chatbox)
    void onInputChanged() {
        if (!etChatbox.getText().toString().isEmpty()) {
            tvSend.setVisibility(View.VISIBLE);
            ivSend.setVisibility(View.INVISIBLE);
        }
    }

    private void sendChat() {
        String message = etChatbox.getText().toString();
        Log.i("mes", message);
        if (!message.equals("")) {
            Map<String, String> body = new HashMap<String, String>() {{
                put("registrationTokenTo", user.getTokenFcm());
                put("registrationTokenFrom", getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN));
                put("message", message);
            }};

            Konsultasi konsultasi = new Konsultasi(user.getTokenFcm(), message, DateUtils.getTime(), user);
            konsultasi.setRegistrationTokenFrom(getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN));
            adapter.addChat(konsultasi, this);
            getViewModel().sendChat(konsultasi, body);
            etChatbox.setText("");
        }

    }

    class ToolbarViewHolder {
        @BindView(R.id.sdv_toolbar_konsultasi_friend_picture)
        SimpleDraweeView sdvFriendPicture;

        @BindView(R.id.tv_toolbar_konsultasi_friend_name)
        TextView tvFriendName;
        @BindView(R.id.tv_toolbar_konsultasi_friend_last_seen)
        TextView tvFriendLastSeen;

        ToolbarViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
