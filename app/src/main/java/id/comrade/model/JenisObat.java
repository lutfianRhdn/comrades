package id.comrade.model;

import com.google.gson.annotations.SerializedName;

public class JenisObat {
    @SerializedName("id_jenis")
    private int idJenis;
    private String nama;
    private String note;

    public int getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(int idJenis) {
        this.idJenis = idJenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
