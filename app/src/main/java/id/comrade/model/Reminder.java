package id.comrade.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "reminder")
public class Reminder implements Parcelable {
    @PrimaryKey
    @SerializedName("id_reminder")
    private long id;
    @SerializedName("id_obat")
    @Ignore
    private int idObat;
    @SerializedName("id_user")
    @Ignore
    private int idUser;
    private String namaObat;
    @SerializedName("waktu_reminder")
    private String reminder;
    @ColumnInfo(name = "starting_time")
    @SerializedName("jam_mulai")
    private String startingTime;
    @Ignore
    private Obat obat;

    public Reminder(){

    }

    @Ignore
    public Reminder(String namaObat, String reminder, String startingTime) {
        this.namaObat = namaObat;
        this.reminder = reminder;
        this.startingTime = startingTime;
    }

    protected Reminder(Parcel in) {
        id = in.readLong();
        idObat = in.readInt();
        idUser = in.readInt();
        namaObat = in.readString();
        reminder = in.readString();
        startingTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(idObat);
        dest.writeInt(idUser);
        dest.writeString(namaObat);
        dest.writeString(reminder);
        dest.writeString(startingTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public int getIdObat() {
        return idObat;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reminder)) {
            return false;
        }

        Reminder reminder = (Reminder) obj;
        return reminder.getId() == this.getId();
    }

    public Obat getObat() {
        return obat;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }
}
