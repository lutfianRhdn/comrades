package id.comrade.editprofile;

import id.comrade.base.BaseViewState;
import id.comrade.model.User;

public class EditProfileViewState extends BaseViewState {
    private String message;
    private User user;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
