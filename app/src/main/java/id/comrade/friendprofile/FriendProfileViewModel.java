package id.comrade.friendprofile;

import android.app.Application;

import id.comrade.base.BaseViewModel;
import id.comrade.model.User;

public class FriendProfileViewModel extends BaseViewModel<FriendProfileViewState> {
    public FriendProfileViewModel(Application app) {
        super(app);

        FriendProfileViewState viewState = new FriendProfileViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void setUser(User user) {
        FriendProfileViewState viewState = getViewState().getValue();
        viewState.setUser(user);
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public User getUser() {
        return getViewState().getValue().getUser();
    }
}
