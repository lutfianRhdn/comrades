package id.comrade.konsultasi;

import java.util.ArrayList;
import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Konsultasi;

class KonsultasiViewState extends BaseViewState {
    private List<Konsultasi> konsultasis = new ArrayList<>();

    public void addKonsultasi(Konsultasi konsultasi) {
        konsultasis.add(konsultasi);
    }

    public List<Konsultasi> getKonsultasis() {
        return konsultasis;
    }

    public void setKonsultasis(List<Konsultasi> konsultasis) {
        this.konsultasis = konsultasis;
    }
}
