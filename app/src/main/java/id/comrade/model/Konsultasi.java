package id.comrade.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "konsultasi")
public class Konsultasi {
    @SerializedName("id_chat")
    @PrimaryKey
    private int idChat;
    @SerializedName("registration_token_to")
    private String registrationTokenTo;
    @SerializedName("registration_token_from")
    private String registrationTokenFrom;
    private String message;
    private String time;
    @Ignore
    private User userTo;

    public Konsultasi() {
    }

    @Ignore
    public Konsultasi(String registrationTokenTo, String message, String time, User userTo) {
        this.registrationTokenTo = registrationTokenTo;
        this.message = message;
        this.time = time;
        this.userTo = userTo;
    }

    public String getRegistrationTokenFrom() {
        return registrationTokenFrom;
    }

    public void setRegistrationTokenFrom(String registrationTokenFrom) {
        this.registrationTokenFrom = registrationTokenFrom;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getRegistrationTokenTo() {
        return registrationTokenTo;
    }

    public void setRegistrationTokenTo(String registrationTokenTo) {
        this.registrationTokenTo = registrationTokenTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }
}
