package id.comrade.editprofile;

import android.app.Application;

import id.comrade.R;
import id.comrade.base.BaseViewModel;
import id.comrade.model.User;
import id.comrade.repositories.UserRepository;

public class EditProfileViewModel extends BaseViewModel<EditProfileViewState> {
    private UserRepository userRepository = UserRepository.getInstance();

    public EditProfileViewModel(Application app) {
        super(app);

        EditProfileViewState viewState = new EditProfileViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void save(User user) {
        EditProfileViewState viewState = getViewState().getValue();
        viewState.setLoading(true);
        getViewState().setValue(viewState);

        addDisposable(userRepository.editProfile(user)
                .subscribe(response -> {
                    if (response.code() == 200) {
                        viewState.setLoading(false);
                        viewState.setMessage(getApplication().getString(R.string.edit_success));
                        viewState.setUser(user);
                        getViewState().setValue(viewState);
                    }
                }, throwable -> {
                    viewState.setLoading(false);
                    viewState.setError(throwable.getMessage());
                    getViewState().setValue(viewState);
                }));
    }
}
