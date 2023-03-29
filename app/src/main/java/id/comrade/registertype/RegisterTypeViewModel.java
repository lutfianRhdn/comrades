package id.comrade.registertype;

import android.app.Application;

import id.comrade.R;
import id.comrade.base.BaseViewModel;
import id.comrade.model.User;
import id.comrade.repositories.UserRepository;

public class RegisterTypeViewModel extends BaseViewModel<RegisterTypeViewState> {
    private UserRepository userRepository = UserRepository.getInstance();

    public RegisterTypeViewModel(Application app) {
        super(app);
        RegisterTypeViewState viewState = new RegisterTypeViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void registerAsUser(User body) {
        RegisterTypeViewState viewState = getViewState().getValue();
        viewState.setLoading(true, RegisterTypeViewState.PROCESS_USER);
        viewState.setMessage(null);
        viewState.setError(null);
        getViewState().setValue(viewState);
        addDisposable(userRepository.register(body)
                .subscribe(response -> {
                    viewState.setLoading(false);
                    if (response.code() == 200) {
                        viewState.setMessage(getApplication().getString(R.string.registration_success));
                    } else if (response.code() == 400) {
                        viewState.setError(getApplication().getString(R.string.email_already_exist));
                    }
                    getViewState().setValue(viewState);
                }));
    }
}
