package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Event implements Parcelable {

    private String _id;
    private String nama;
    private String pengirim;
    private String tempat;
    private String deskripsi;
    private String status;
    @SerializedName("tgl_posting")
    private String tglPosting;
    private String tglMulai;
    private String tglBerakhir;
    private Double latitude;
    private String type;
    private Double longitude;
    private String kontakPerson;
    private String lang;
    private String foto;
    private Integer v;
    private String slug;

    protected Event(Parcel in){
        _id = in.readString();
        nama = in.readString();
        pengirim = in.readString();
        tempat = in.readString();
        deskripsi = in.readString();
        status = in.readString();
        tglPosting = in.readString();
        tglMulai = in.readString();
        tglBerakhir = in.readString();
        latitude = in.readDouble();
        type = in.readString();
        longitude = in.readDouble();
        kontakPerson = in.readString();
        lang = in.readString();
        foto = in.readString();
//        v = in.readInt();
        slug = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(nama);
        dest.writeString(pengirim);
        dest.writeString(tempat);
        dest.writeString(deskripsi);
        dest.writeString(status);
        dest.writeString(tglPosting);
        dest.writeString(tglMulai);
        dest.writeString(tglBerakhir);
        dest.writeDouble(latitude);
        dest.writeString(type);
        dest.writeDouble(longitude);
        dest.writeString(kontakPerson);
        dest.writeString(lang);
        dest.writeString(foto);
//        dest.writeInt(v);
        dest.writeString(slug);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getTglPosting() {
        return tglPosting;
    }

    public void setTglPosting(String tglPosting) {
        this.tglPosting = tglPosting;
    }

    public String getTglMulai() {
        return tglMulai;
    }

    public void setTglMulai(String tglMulai) {
        this.tglMulai = tglMulai;
    }

    public String getTglBerakhir() {
        return tglBerakhir;
    }

    public void setTglBerakhir(String tglBerakhir) {
        this.tglBerakhir = tglBerakhir;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getKontakPerson() {
        return kontakPerson;
    }

    public void setKontakPerson(String kontakPerson) {
        this.kontakPerson = kontakPerson;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }




}
