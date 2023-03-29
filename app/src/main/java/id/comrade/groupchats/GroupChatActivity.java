package id.comrade.groupchats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;



import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;

public class GroupChatActivity extends BaseActivity<GroupChatViewState, GroupChatViewModel> {
    private static final String TAG = GroupChatActivity.class.getName();

    @BindView(R.id.toolbar_activity_group_chat)
    Toolbar toolbar;

    @BindView(R.id.rv_activity_group_chat)
    RecyclerView rvChats;

    @BindView(R.id.et_activity_group_chat_chatbox)
    EditText etChatbox;

    @BindView(R.id.tv_activity_group_chat_send)
    TextView tvSend;

    private GroupChatAdapter chatAdapter;
//    private IRainbowConversation rainbowConversation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        init(GroupChatViewModel.class);
//
//        chatAdapter = new GroupChatAdapter();
//        rvChats.setAdapter(chatAdapter);
//
//        RainbowSdk.instance().connection().start(new StartResponseListener() {
//            @Override
//            public void onStartSucceeded() {
//                RainbowSdk.instance().connection()
//                        .signin("tiorezafk@outlook.com", "]*qa5dyYBg@8", "sandbox.openrainbow.com", new SigninResponseListener() {
//                            @Override
//                            public void onSigninSucceeded() {
//                                runOnUiThread(GroupChatActivity.this::fetchChats);
//                            }
//
//                            @Override
//                            public void onRequestFailed(final RainbowSdk.ErrorCode errorCode, final String s) {
//                                runOnUiThread(() -> showToast(s));
//                            }
//                        });
//            }
//
//            @Override
//            public void onRequestFailed(RainbowSdk.ErrorCode errorCode, String err) {
//                Log.e(TAG, err);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatAdapter = null;
    }

    @Override
    protected void handleViewState(GroupChatViewState viewState) {
        handleMessage(viewState.getMessage());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_group_chat;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    private void handleMessage(String message) {

    }

    private void fetchChats() {
//        Bubbles bubbles = new Bubbles();
//        Room room = bubbles.findBubbleByName(getString(R.string.app_name));
//
//        rainbowConversation = RainbowSdk.instance().conversations().getConversationFromRoom(room,
//                new IRainbowGetConversationListener() {
//                    @Override
//                    public void onGetConversationSuccess(IRainbowConversation iRainbowConversation) {
//                        ArrayItemList<IMMessage> messages = iRainbowConversation.getMessages();
//                        for (int i = 0; i < messages.getCount(); i++) {
//                            Log.i(TAG, "Item ke-" + i + " " + messages.get(i).getMessageContent());
//                        }
//                    }
//
//                    @Override
//                    public void onGetConversationError() {
//                    }
//                });
//        Log.i(TAG, "rainbowConversation Count: " + rainbowConversation.getMessages().getCount());
//        rainbowConversation.getMessages().registerChangeListener(() -> {
//            ArrayItemList<IMMessage> messages = rainbowConversation.getMessages();
//            for (int i = 0; i < messages.getCount(); i++) {
//                Log.i(TAG, "Item ke-" + i + " " + messages.get(i).getMessageContent());
//            }
//        });
    }

}
