package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Posting implements Parcelable {
    private String _id;
    private String pengirim;
    private String judul;
    private String isi;
    private String deskripsi;
    private String foto;
    private String status;
    @SerializedName("tgl_posting")
    private String tanggalTerbit;
    private String kategori;
    private String sumber;
    private String lang;
    private String slug;

    protected Posting(Parcel in) {
        _id = in.readString();
        pengirim = in.readString();
        judul = in.readString();
        isi = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        status = in.readString();
        tanggalTerbit = in.readString();
        kategori = in.readString();
        sumber = in.readString();
        lang = in.readString();
        slug = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(pengirim);
        dest.writeString(judul);
        dest.writeString(isi);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeString(status);
        dest.writeString(tanggalTerbit);
        dest.writeString(kategori);
        dest.writeString(sumber);
        dest.writeString(lang);
        dest.writeString(slug);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Posting> CREATOR = new Creator<Posting>() {
        @Override
        public Posting createFromParcel(Parcel in) {
            return new Posting(in);
        }

        @Override
        public Posting[] newArray(int size) {
            return new Posting[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(String tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
