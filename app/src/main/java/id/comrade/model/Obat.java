package id.comrade.model;

import com.google.gson.annotations.SerializedName;

public class Obat {
    @SerializedName("id_obat")
    private int idObat;
    private String merek;
    private String produsen;
    private String kandungan;
    private String bentuk;
    @SerializedName("jenis_obat")
    private JenisObat jenisObat;

    public int getIdObat() {
        return idObat;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    public String getKandungan() {
        return kandungan;
    }

    public void setKandungan(String kandungan) {
        this.kandungan = kandungan;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public JenisObat getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(JenisObat jenisObat) {
        this.jenisObat = jenisObat;
    }
}
