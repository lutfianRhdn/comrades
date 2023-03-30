package id.comrade.login;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;

import id.comrade.base.BaseViewModel;
import id.comrade.model.Reminder;
import id.comrade.model.RootResponse;
import id.comrade.model.User;
import id.comrade.repositories.ReminderCache;
import id.comrade.repositories.UserRepository;
import id.comrade.utils.AlarmUtils;

public class LoginViewModel extends BaseViewModel<LoginViewState> {
    private UserRepository userRepository = UserRepository.getInstance();
    private ReminderCache reminderCache;

    public LoginViewModel(Application app) {
        super(app);
        reminderCache = ReminderCache.getInstance(app);

        LoginViewState viewState = new LoginViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void login(String email, String password, String fcmToken, String loginType) {
        LoginViewState viewState = getViewState().getValue();
        viewState.setLoading(true);
        viewState.setError(null);
        getViewState().setValue(viewState);

        User body = new User() {{
            setEmail(email);
            setPassword(password);
            setTokenFcm(fcmToken);
            setLoginType(loginType);
        }};

        addDisposable(userRepository.login(body)
                .subscribe(response -> {
                    Log.d(String.valueOf(response.code()), "login: response");
                    Log.d(String.valueOf(response.headers().toString()), "login: response hea");
                    if (response.code() == 200) {

                        viewState.setLoginResponse(response.body().getResult());
                    } else {
                        Gson gson = new Gson();
                        String jsonError = response.errorBody().toString();
                        RootResponse rootResponse = gson.fromJson(jsonError, RootResponse.class);

                        viewState.setLoading(false);
                        viewState.setError(rootResponse.getMessage());
                    }
                    getViewState().setValue(viewState);
                }, error -> {
                    viewState.setLoading(false);
                    viewState.setError(error.getMessage());
                    getViewState().setValue(viewState);
                }));
    }

    @SuppressWarnings("ConstantConditions")
    public void login(String email, String password, String fcmToken) {
        login(email, password, fcmToken, "default");
    }

    public void clear() {
        getViewState().setValue(null);
    }

    @SuppressWarnings("ConstantConditions")
    public void migrateReminder(int userId) {
        LoginViewState viewState = getViewState().getValue();
        addDisposable(reminderCache.findAllFromServer(userId)
                .subscribe(reminders -> {
                    for (Reminder reminder : reminders) {
                        reminder.setNamaObat(reminder.getObat().getMerek());
                        AlarmUtils.set(getApplication(), reminder);
                    }
                    addDisposable(reminderCache.saveAll(reminders).subscribe(() -> {
                        viewState.setLoading(false);
                        getViewState().setValue(viewState);
                    }));
                }));
    }
}
