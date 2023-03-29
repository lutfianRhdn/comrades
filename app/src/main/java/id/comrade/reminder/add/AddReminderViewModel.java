package id.comrade.reminder.add;

import android.app.Application;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.JsonObject;

import java.util.List;

import id.comrade.base.BaseViewModel;
import id.comrade.model.Obat;
import id.comrade.model.Reminder;
import id.comrade.repositories.ObatCache;
import id.comrade.repositories.ReminderCache;
import id.comrade.utils.AlarmUtils;

public class AddReminderViewModel extends BaseViewModel<AddReminderViewState> {
    private ReminderCache reminderCache;
    private ObatCache obatCache;

    public AddReminderViewModel(Application app) {
        super(app);
        reminderCache = ReminderCache.getInstance(app);
        obatCache = ObatCache.getInstance();

        AddReminderViewState addReminderViewState = new AddReminderViewState();
        getViewState().setValue(addReminderViewState);

        addDisposable(obatCache.getCache()
                .subscribe(obats -> filter(obats, addReminderViewState)));
    }

    @SuppressWarnings("ConstantConditions")
    public void saveReminder(Reminder reminder) {
        AddReminderViewState addReminderViewState = getViewState().getValue();
        addReminderViewState.setLoading(true);
        addReminderViewState.setError(null);
        getViewState().setValue(addReminderViewState);

        addDisposable(reminderCache.saveToServer(reminder).subscribe(response -> {
            JsonObject resultJson = response.get("result").getAsJsonObject();
            reminder.setId(resultJson.get("id_reminder").getAsInt());
            addDisposable(reminderCache.save(reminder).subscribe(newReminderId -> {
                addReminderViewState.setLoading(false);
                addReminderViewState.setFinish(true);
                getViewState().setValue(addReminderViewState);

                reminderCache.addCache(reminder);
                AlarmUtils.set(getApplication(), reminder);
            }));
        }, throwable -> {
            addReminderViewState.setError(throwable.getMessage());
            getViewState().setValue(addReminderViewState);
        }));
    }

    private void filter(List<Obat> obats, AddReminderViewState addReminderViewState) {
        addDisposable(reminderCache.getCache()
                .subscribe(reminders -> {
                    List<Obat> filteredObat = Stream.of(obats)
                            .filter(obat -> {
                                for (Reminder reminder : reminders) {
                                    if (reminder.getNamaObat().equals(obat.getMerek())) {
                                        return false;
                                    }
                                }
                                return true;
                            })
                            .collect(Collectors.toList());

                    addReminderViewState.setObats(filteredObat);
                    getViewState().setValue(addReminderViewState);
                }));
    }
}
