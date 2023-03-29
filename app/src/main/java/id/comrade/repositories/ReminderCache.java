package id.comrade.repositories;

import android.app.Application;

import com.google.gson.JsonObject;

import java.util.List;

import id.comrade.model.Reminder;
import id.comrade.model.RootResponse;
import id.comrade.services.database.AppDatabase;
import id.comrade.services.database.ReminderDao;
import id.comrade.services.webservice.ReminderApi;
import id.comrade.services.webservice.RetrofitService;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReminderCache extends Cache<Reminder> {
    private static ReminderCache instance;

    private ReminderDao reminderDao;
    private ReminderApi reminderApi;

    private ReminderCache(Application app) {
        reminderDao = AppDatabase.getAppDatabase(app).getReminderDao();
        reminderApi = RetrofitService.getInstance().create(ReminderApi.class);
    }

    public static ReminderCache getInstance(Application app) {
        if (instance == null) {
            instance = new ReminderCache(app);
        }

        return instance;
    }

    @Override
    protected Single<List<Reminder>> retrieve() {
        return reminderDao.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    setCache(response);
                    return Single.just(response);
                });
    }

    public Single<List<Reminder>> findAllFromServer(int userId) {
        return reminderApi.findAll(userId)
                .map(RootResponse::getResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Long> save(Reminder reminder) {
        return Single
                .fromCallable(() -> reminderDao.insert(reminder))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<JsonObject> saveToServer(Reminder reminder) {
        return reminderApi.saveReminder(reminder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable update(Reminder reminder) {
        return Completable
                .fromAction(() -> reminderDao.update(reminder))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<JsonObject> updateToServer(Reminder reminder) {
        return reminderApi.updateReminder(reminder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable delete(Reminder reminder) {
        return Completable
                .fromAction(() -> reminderDao.delete(reminder))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<JsonObject> deleteFromServer(Reminder reminder) {
        return reminderApi.deleteReminder(reminder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteAll() {
        return Completable
                .fromAction(() -> reminderDao.deleteAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable saveAll(List<Reminder> reminders) {
        return Completable
                .fromAction(() -> reminderDao.insert(reminders))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
