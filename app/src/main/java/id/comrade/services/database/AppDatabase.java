package id.comrade.services.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import id.comrade.model.Konsultasi;
import id.comrade.model.Reminder;

@Database(entities = {Reminder.class, Konsultasi.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "db:comrade";

    private static AppDatabase instance;

    public abstract ReminderDao getReminderDao();

    public abstract KonsultasiDao getKonsultasiDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE konsultasi (`idChat` INTEGER PRIMARY KEY NOT NULL, " +
                    "`registrationTokenTo` TEXT, " +
                    "`message` TEXT, `time` TEXT) ");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE konsultasi ADD COLUMN `registrationTokenFrom` TEXT");
        }
    };

    private static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE konsultasi (`idChat` INTEGER PRIMARY KEY NOT NULL, " +
                    "`registrationTokenTo` TEXT, " +
                    "`registrationTokenFrom` TEXT, " +
                    "`message` TEXT, `time` TEXT) ");
        }
    };

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2, MIGRATION_1_3, MIGRATION_2_3)
                    .build();
        }
        return instance;
    }
}
