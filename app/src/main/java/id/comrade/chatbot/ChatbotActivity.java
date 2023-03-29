package id.comrade.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.groupchats.GroupChatActivity;
import id.comrade.model.Chat;
import id.comrade.model.User;
import id.comrade.utils.DateUtils;

public class ChatbotActivity extends BaseActivity<ChatbotViewState, ChatbotViewModel> {

    public static String MESSAGE_EXTRA = "intent-extra:message";

    @BindView(R.id.et_activity_chatbot_chatbox)
    EditText etChatbox;

    @BindView(R.id.tv_activity_chatbot_send)
    TextView tvSend;

    @BindView(R.id.rv_activity_chatbot)
    RecyclerView rvChats;

    @BindView(R.id.toolbar_activity_chatbot)
    Toolbar toolbar;

    private ChatbotAdapter chatAdapter;


    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(ChatbotViewModel.class);

        chatAdapter = new ChatbotAdapter(this);
        rvChats.setAdapter(chatAdapter);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            send(new Chat(
                    intent.getStringExtra(MESSAGE_EXTRA),
                    DateUtils.getDate(),
                    DateUtils.getTime(),
                    Chat.READ,
                    ChatbotAdapter.CHATBOT_SEND_TO,
                    ChatbotAdapter.CHATBOT_NAME
            ));
        }

        tvSend.setOnClickListener(v -> sendChat());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatAdapter = null;
    }

    @Override
    protected void handleViewState(ChatbotViewState viewState) {
        observeChat(viewState.getChats());
        handleError(viewState.getError());
        send(viewState.getChat());
    }

    @OnTextChanged(R.id.et_activity_chatbot_chatbox)
    void onInputChanged() {
        if (etChatbox.getText().toString().isEmpty()) {
            tvSend.setEnabled(false);
        } else {
            tvSend.setEnabled(true);
        }
    }

    private void handleError(String errorMessage) {
        if (errorMessage != null) {
            showToast(errorMessage);
        }
    }

    private void observeChat(List<Chat> chats) {
        if (chats == null || chats.size() == 0) {
            return;
        }
        int lastChatPosition = chats.size() - 1;
        Chat lastChat = chats.get(lastChatPosition);
        if (lastChat.getMessage().equals(getString(R.string.response_group_chat)) &&
                lastChat.getUidSender().equals(ChatbotAdapter.CHATBOT_NAME)) {
//            Intent intent = new Intent(RainbowSdk.instance().getContext(), GroupChatActivity.class);
//            startActivity(intent);
        }
        if (lastChat.getUidRecevier().equals(ChatbotAdapter.CHATBOT_NAME)) {
            getViewModel().retrieveResponse(lastChat.getMessage(),Integer.valueOf(getSharedPreferences(User.PREF_USER_ID)));
        }
    }

    private void send(Chat chat) {
        if (chat == null) {
            return;
        }
        chatAdapter.addChat(chat);

        List<Chat> chats = chatAdapter.getChats();
        getViewModel().setChats(chats);
        int lastChatPosition = chats.size();
        rvChats.smoothScrollToPosition(lastChatPosition);
    }

    private void sendChat() {
        String message = etChatbox.getText().toString();

        Chat chat = new Chat(
                message,
                DateUtils.getDate(),
                DateUtils.getTime(),
                Chat.NOT_READ,
                getSharedPreferences(User.PREF_USER_UID),
                ChatbotAdapter.CHATBOT_NAME
        );
        send(chat);
        etChatbox.setText("");
    }

    @Override
    protected String getToolbarTitle() {
        return ChatbotAdapter.CHATBOT_NAME;
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chatbot;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }
}