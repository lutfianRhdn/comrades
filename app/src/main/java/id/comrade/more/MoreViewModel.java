package id.comrade.more;

import android.app.Application;

import com.annimon.stream.Stream;

import id.comrade.base.BaseViewModel;
import id.comrade.repositories.ReminderCache;
import id.comrade.utils.AlarmUtils;

public class MoreViewModel extends BaseViewModel<MoreViewState> {
    private ReminderCache reminderCache;

    public MoreViewModel(Application app) {
        super(app);

        reminderCache = ReminderCache.getInstance(app);

        MoreViewState viewState = new MoreViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void logout() {
        MoreViewState viewState = getViewState().getValue();
        addDisposable(reminderCache.getCache()
                .subscribe(reminders -> {
                    if (reminders != null) {
                        Stream.of(reminders)
                                .forEach(reminder -> AlarmUtils.cancel(getApplication(), reminder));
                        addDisposable(reminderCache.deleteAll().subscribe());
                        viewState.setLogout(true);
                        getViewState().setValue(viewState);
                    }
                }));
    }
}
