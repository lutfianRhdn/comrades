package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tweet implements Parcelable {

    @SerializedName("id_string")
    @Expose
    private String idString;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("profile_image_url")
    @Expose
    private String profileImageUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("klasifikasi")
    @Expose
    private String klasifikasi;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_token")
    @Expose
    private Integer statusToken;
    @SerializedName("profile_link_color")
    @Expose
    private String profileLinkColor;

    protected Tweet(Parcel in){
        idString = in.readString();
        screenName = in.readString();
        text = in.readString();
        profileImageUrl = in.readString();
        createdAt = in.readString();
        klasifikasi = in.readString();
        status = in.readString();
        statusToken = in.readInt();
        profileLinkColor = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idString);
        dest.writeString(screenName);
        dest.writeString(text);
        dest.writeString(profileImageUrl);
        dest.writeString(createdAt);
        dest.writeString(klasifikasi);
        dest.writeString(status);
        dest.writeInt(statusToken);
        dest.writeString(profileLinkColor);
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {return new Tweet[size]; }
    };


    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        this.klasifikasi = klasifikasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusToken() {
        return statusToken;
    }

    public void setStatusToken(Integer statusToken) {
        this.statusToken = statusToken;
    }

    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }
}
