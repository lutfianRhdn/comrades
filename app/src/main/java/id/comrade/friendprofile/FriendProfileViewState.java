package id.comrade.friendprofile;

import id.comrade.base.BaseViewState;
import id.comrade.model.User;

public class FriendProfileViewState extends BaseViewState {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
