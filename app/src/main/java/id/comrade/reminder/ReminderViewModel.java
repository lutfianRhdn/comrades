package id.comrade.reminder;

import android.app.Application;

import id.comrade.R;
import id.comrade.base.BaseViewModel;
import id.comrade.model.Reminder;
import id.comrade.repositories.ReminderCache;
import id.comrade.utils.AlarmUtils;

public class ReminderViewModel extends BaseViewModel<ReminderViewState> {
    private ReminderCache reminderCache;

    public ReminderViewModel(Application app) {
        super(app);

        reminderCache = ReminderCache.getInstance(app);

        ReminderViewState viewState = new ReminderViewState();
        getViewState().setValue(viewState);

        addDisposable(reminderCache.getCache()
                .subscribe(reminders -> {
                    viewState.setLoading(false);
                    viewState.setReminders(reminders);
                    getViewState().setValue(viewState);
                }));
    }

    @SuppressWarnings("ConstantConditions")
    public void delete(Reminder reminder) {
        ReminderViewState viewState = getViewState().getValue();
        viewState.setLoading(false);
        viewState.setMessage(null);
        viewState.setReminders(null);
        getViewState().setValue(viewState);

        addDisposable(reminderCache.deleteFromServer(reminder).subscribe());
        addDisposable(reminderCache.delete(reminder)
                .subscribe(() -> {
                    viewState.setMessage(getApplication().getString(R.string.success_delete_reminder));
                    reminderCache.removeCache(reminder);
                    AlarmUtils.cancel(getApplication(), reminder);
                    getViewState().setValue(viewState);

                    reminderCache.getCache()
                            .subscribe(reminders -> {
                                viewState.setMessage(null);
                                viewState.setReminders(reminders);
                                getViewState().setValue(viewState);
                            });
                }));
    }
}
