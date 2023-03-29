package id.comrade.friendtoshare;

import android.app.Application;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import id.comrade.base.BaseViewModel;
import id.comrade.model.User;
import id.comrade.repositories.OdhaCache;
import id.comrade.repositories.SahabatOdhaCache;

public class FriendToShareViewModel extends BaseViewModel<FriendToShareViewState> {

    private OdhaCache odhaCache = OdhaCache.getInstance();
    private SahabatOdhaCache sahabatOdhaCache = SahabatOdhaCache.getInstance();

    private List<User> users;

    public FriendToShareViewModel(Application app) {
        super(app);
        FriendToShareViewState viewState = new FriendToShareViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void findSahabat(String userType) {
        FriendToShareViewState viewState = getViewState().getValue();

        if (userType.equals(User.USER_SAHABAT_ODHA)) {
            addDisposable(odhaCache.getCache()
                    .subscribe(odha -> onSuccessResponse(odha, viewState),
                            error -> onErrorResponse(error, viewState)));
        } else {
            addDisposable(sahabatOdhaCache.getCache()
                    .subscribe(odha -> onSuccessResponse(odha, viewState),
                            error -> onErrorResponse(error, viewState)));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void filter(String name) {
        if (users != null) {
            List<User> filteredUser = Stream.of(users)
                    .filter(user -> user.getNama().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());

            FriendToShareViewState viewState = getViewState().getValue();
            viewState.setUsers(filteredUser);
            getViewState().setValue(viewState);
        }
    }

    private void onSuccessResponse(List<User> users, FriendToShareViewState viewState) {
        this.users = users;
        viewState.setLoading(false);
        viewState.setError(null);
        viewState.setUsers(users);
        getViewState().setValue(viewState);
    }

    private void onErrorResponse(Throwable error, FriendToShareViewState viewState) {
        error.printStackTrace();
        viewState.setLoading(false);
        viewState.setError(error.getMessage());
        viewState.setUsers(null);
        getViewState().setValue(viewState);
    }
}
