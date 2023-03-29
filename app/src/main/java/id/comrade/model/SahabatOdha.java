package id.comrade.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SahabatOdha implements Parcelable {
    @SerializedName("id_sahabatodha")
    private int idSahabatOdha;
    private String komunitas;
    private String pekerjaan;
    private String institusi;
    private int usia;
    @SerializedName("about_sahabatodha")
    private String about;
    @SerializedName("status_aktivasi")
    private String statusAktivasi;
    private int harga;
    @SerializedName("asal_kota")
    private String kota;

    protected SahabatOdha(Parcel in) {
        idSahabatOdha = in.readInt();
        komunitas = in.readString();
        pekerjaan = in.readString();
        institusi = in.readString();
        usia = in.readInt();
        about = in.readString();
        statusAktivasi = in.readString();
        harga = in.readInt();
        kota = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idSahabatOdha);
        dest.writeString(komunitas);
        dest.writeString(pekerjaan);
        dest.writeString(institusi);
        dest.writeInt(usia);
        dest.writeString(about);
        dest.writeString(statusAktivasi);
        dest.writeInt(harga);
        dest.writeString(kota);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SahabatOdha> CREATOR = new Creator<SahabatOdha>() {
        @Override
        public SahabatOdha createFromParcel(Parcel in) {
            return new SahabatOdha(in);
        }

        @Override
        public SahabatOdha[] newArray(int size) {
            return new SahabatOdha[size];
        }
    };

    public int getIdSahabatOdha() {
        return idSahabatOdha;
    }

    public void setIdSahabatOdha(int idSahabatOdha) {
        this.idSahabatOdha = idSahabatOdha;
    }

    public String getKomunitas() {
        return komunitas;
    }

    public void setKomunitas(String komunitas) {
        this.komunitas = komunitas;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getInstitusi() {
        return institusi;
    }

    public void setInstitusi(String institusi) {
        this.institusi = institusi;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatusAktivasi() {
        return statusAktivasi;
    }

    public void setStatusAktivasi(String statusAktivasi) {
        this.statusAktivasi = statusAktivasi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
