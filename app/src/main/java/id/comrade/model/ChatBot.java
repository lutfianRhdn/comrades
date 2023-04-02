package id.comrade.model;

import com.google.gson.annotations.SerializedName;

public class ChatBot {
    private int status;
    private String message;
    private String result;
    @SerializedName("text")
    private String text;
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
    public void setText(String text) {
        this.text = text;
    }
}
