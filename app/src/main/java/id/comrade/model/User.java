package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    public static final String PREF_USER_ID = "preference:userId";
    public static final String PREF_USER_UID = "preference:userUid";
    public static final String PREF_USER_NAME = "preference:userName";
    public static final String PREF_USER_EMAIL = "preference:userEmail";
    public static final String PREF_USER_GENDER = "preference:userGender";
    public static final String PREF_USER_PHONE = "preference:userPhone";
    public static final String PREF_USER_PHOTO = "preference:userPhoto";
    public static final String PREF_USER_TYPE = "preference:userType";
    public static final String PREF_USER_BIRTH = "preference:userBirth";

    public static final String USER_ODHA = "Odha";
    public static final String USER_SAHABAT_ODHA = "Sahabat Odha";

    @SerializedName("id_user")
    private int idUser;
    private String nama;
    private String email;
    private String jk;
    private String telp;
    @SerializedName("tgl_lahir")
    private String tanggalLahir;
    private String status;
    @SerializedName("jenis_user")
    private String jenisUser;
    private String foto;
    @SerializedName("private_key")
    private String privateKey;
    @SerializedName("registration_token_fcm")
    private String tokenFcm;
    @SerializedName("sahabat_odha")
    private SahabatOdha sahabatOdha;
    private Rating[] ratings;
    private String password;
    @SerializedName("typeLogin")
    private String loginType;

    public User() {
    }

    protected User(Parcel in) {
        idUser = in.readInt();
        nama = in.readString();
        email = in.readString();
        jk = in.readString();
        telp = in.readString();
        tanggalLahir = in.readString();
        status = in.readString();
        jenisUser = in.readString();
        foto = in.readString();
        privateKey = in.readString();
        tokenFcm = in.readString();
        sahabatOdha = in.readParcelable(SahabatOdha.class.getClassLoader());
        ratings = in.createTypedArray(Rating.CREATOR);
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUser);
        dest.writeString(nama);
        dest.writeString(email);
        dest.writeString(jk);
        dest.writeString(telp);
        dest.writeString(tanggalLahir);
        dest.writeString(status);
        dest.writeString(jenisUser);
        dest.writeString(foto);
        dest.writeString(privateKey);
        dest.writeString(tokenFcm);
        dest.writeParcelable(sahabatOdha, flags);
        dest.writeTypedArray(ratings, flags);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public SahabatOdha getSahabatOdha() {
        return sahabatOdha;
    }

    public void setSahabatOdha(SahabatOdha sahabatOdha) {
        this.sahabatOdha = sahabatOdha;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenisUser() {
        return jenisUser;
    }

    public void setJenisUser(String jenisUser) {
        this.jenisUser = jenisUser;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTokenFcm() {
        return tokenFcm;
    }

    public void setTokenFcm(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }

    public Rating[] getRatings() {
        return ratings;
    }

    public void setRatings(Rating[] ratings) {
        this.ratings = ratings;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
