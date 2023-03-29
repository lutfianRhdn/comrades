package id.comrade.model;

import java.util.List;

public class ChatSharingFriend extends Chat {
    private List<User> mFriends;

    public ChatSharingFriend(String message, String date, String time, int status, String uidSender,
                             String uidRecevier, List<User> mFriends) {
        super(message, date, time, status, uidSender, uidRecevier);
        this.mFriends = mFriends;
    }

    public List<User> getFriends() {
        return mFriends;
    }

    public void setFriends(List<User> mFriends) {
        this.mFriends = mFriends;
    }
}
