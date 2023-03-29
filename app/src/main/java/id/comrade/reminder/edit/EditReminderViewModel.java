package id.comrade.reminder.edit;

import android.app.Application;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import id.comrade.R;
import id.comrade.base.BaseViewModel;
import id.comrade.model.Obat;
import id.comrade.model.Reminder;
import id.comrade.repositories.ObatCache;
import id.comrade.repositories.ReminderCache;
import id.comrade.utils.AlarmUtils;

public class EditReminderViewModel extends BaseViewModel<EditReminderViewState> {
    private ReminderCache reminderCache;
    private ObatCache obatCache;

    public EditReminderViewModel(Application app) {
        super(app);
        reminderCache = ReminderCache.getInstance(app);
        obatCache = ObatCache.getInstance();

        EditReminderViewState viewState = new EditReminderViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void saveReminder(Reminder reminder) {
        EditReminderViewState viewState = getViewState().getValue();
        viewState.setLoading(true);
        viewState.setMessage(null);
        viewState.setError(null);
        getViewState().setValue(viewState);

        addDisposable(reminderCache.updateToServer(reminder).subscribe(response -> {
            addDisposable(reminderCache.update(reminder).subscribe(() -> {
                viewState.setLoading(false);
                viewState.setMessage(getApplication().getString(R.string.success_edit_reminder));
                getViewState().setValue(viewState);
                AlarmUtils.set(getApplication(), reminder);
            }));
        }, throwable -> {
            viewState.setError(throwable.getMessage());
            getViewState().setValue(viewState);
        }));
    }

    @SuppressWarnings("ConstantConditions")
    public void delete(Reminder reminder) {
        EditReminderViewState viewState = getViewState().getValue();
        viewState.setLoading(true);
        viewState.setMessage(null);
        viewState.setError(null);
        getViewState().setValue(viewState);

        addDisposable(reminderCache.deleteFromServer(reminder).subscribe(response -> {
            addDisposable(reminderCache.delete(reminder).subscribe(() -> {
                viewState.setLoading(false);
                viewState.setMessage(getApplication().getString(R.string.success_delete_reminder));
                getViewState().setValue(viewState);
                reminderCache.removeCache(reminder);
                deleteReminder(reminder);
            }));
        }, throwable -> {
            viewState.setError(throwable.getMessage());
            getViewState().setValue(viewState);
        }));
    }

    public void deleteReminder(Reminder reminder) {
        AlarmUtils.cancel(getApplication(), reminder);
    }

    @SuppressWarnings("ConstantConditions")
    public void filter(Reminder reminder) {
        addDisposable(obatCache.getCache()
                .subscribe(obats -> filter(obats, getViewState().getValue(), reminder)));
    }

    private void filter(List<Obat> obats, EditReminderViewState viewState, Reminder reminderToEdit) {
        addDisposable(reminderCache.getCache()
                .subscribe(reminders -> {
                    List<Obat> filteredObat = Stream.of(obats)
                            .filter(obat -> {
                                for (Reminder reminder : reminders) {
                                    if (reminder.getNamaObat().equals(reminderToEdit.getNamaObat())) {
                                        return true;
                                    }

                                    if (reminder.getNamaObat().equals(obat.getMerek())) {
                                        return false;
                                    }
                                }
                                return true;
                            })
                            .collect(Collectors.toList());

                    viewState.setObats(filteredObat);
                    getViewState().setValue(viewState);
                }));
    }
}
