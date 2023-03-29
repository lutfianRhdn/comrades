package id.comrade.model;

import com.google.gson.annotations.SerializedName;

import id.comrade.utils.DateUtils;

public class Chat {
    public static final int READ = 1;
    public static final int NOT_READ = 0;

    private String message;
    private String date;
    private String time;
    private int status;
    private String uidSender;
    @SerializedName("uid")
    private String uidRecevier;

    public Chat(String message, String date, String time, int status, String uidSender,
                String uidRecevier) {
        this.message = message;
        this.date = DateUtils.getDate();
        this.time = DateUtils.getTime();
        this.status = status;
        this.uidSender = uidSender;
        this.uidRecevier = uidRecevier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUidSender() {
        return uidSender;
    }

    public void setUidSender(String uidSender) {
        this.uidSender = uidSender;
    }

    public String getUidRecevier() {
        return uidRecevier;
    }

    public void setUidRecevier(String uidRecevier) {
        this.uidRecevier = uidRecevier;
    }

    public void setScreenName(String screenName) {
        this.uidRecevier = screenName;
    }
}
