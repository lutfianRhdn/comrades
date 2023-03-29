package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {
    private String _id;
    private String pengirim;
    private String judul;
    private String isi;
    private String deskripsi;
    private String foto;
    private String status;
    @SerializedName("tgl_posting")
    private String tglPosting;
    private String kategori;
    private String sumber;
    private String lang;
    private Integer v;
    private String slug;

    protected Article(Parcel in){
        _id = in.readString();
        pengirim = in.readString();
        judul = in.readString();
        isi = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        status = in.readString();
        tglPosting = in.readString();
        kategori = in.readString();
        sumber = in.readString();
        lang = in.readString();
        v = in.readInt();
        slug = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {return new Article[size];}
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(pengirim);
        dest.writeString(judul);
        dest.writeString(isi);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeString(status);
        dest.writeString(tglPosting);
        dest.writeString(kategori);
        dest.writeString(sumber);
        dest.writeString(lang);
//        dest.writeInt(v);
        dest.writeString(slug);
    }
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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

    public String getTglPosting() {
        return tglPosting;
    }

    public void setTglPosting(String tglPosting) {
        this.tglPosting = tglPosting;
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

