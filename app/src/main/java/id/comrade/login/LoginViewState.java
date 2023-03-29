package id.comrade.login;

import id.comrade.base.BaseViewState;
import id.comrade.model.User;

public class LoginViewState extends BaseViewState {
    private User user;

    public User getLoginResponse() {
        return user;
    }

    public void setLoginResponse(User user) {
        this.user = user;
    }
}
