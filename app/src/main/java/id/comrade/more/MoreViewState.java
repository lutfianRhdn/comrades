package id.comrade.more;

import id.comrade.base.BaseViewState;

public class MoreViewState extends BaseViewState {
    private boolean logout;

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }
}
