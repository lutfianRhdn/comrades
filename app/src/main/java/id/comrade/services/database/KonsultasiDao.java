package id.comrade.services.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import id.comrade.model.Konsultasi;
import io.reactivex.Single;

@Dao
public interface KonsultasiDao {
    @Query("SELECT * FROM konsultasi")
    Single<List<Konsultasi>> findAll();

    @Insert
    long insert(Konsultasi konsultasi);
}
