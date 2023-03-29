package id.comrade.services.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.comrade.model.Reminder;
import io.reactivex.Single;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminder")
    Single<List<Reminder>> findAll();
    @Insert
    long insert(Reminder reminder);
    @Insert
    void insert(List<Reminder> reminder);
    @Update
    void update(Reminder reminder);
    @Delete
    void delete(Reminder reminder);
    @Query("DELETE FROM reminder")
    void deleteAll();
}
