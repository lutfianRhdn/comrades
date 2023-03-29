package id.comrade.chatbot;

import java.util.ArrayList;
import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Chat;

public class ChatbotViewState extends BaseViewState {
    private List<Chat> chats = new ArrayList<>();

    private Chat chat;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
