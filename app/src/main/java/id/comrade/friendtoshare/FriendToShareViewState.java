package id.comrade.friendtoshare;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.User;

public class FriendToShareViewState extends BaseViewState {
    private List<User> users;

    public FriendToShareViewState() {
        setLoading(false);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
