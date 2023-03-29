package id.comrade.konsultasi;

import android.app.Application;
import android.util.Log;

import java.util.Map;

import id.comrade.base.BaseViewModel;
import id.comrade.model.Konsultasi;
import id.comrade.repositories.KonsultasiCache;

public class KonsultasiViewModel extends BaseViewModel<KonsultasiViewState> {
    private KonsultasiCache cache;

    public KonsultasiViewModel(Application app) {
        super(app);
        cache = KonsultasiCache.getInstance(app);

        KonsultasiViewState viewState = new KonsultasiViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    void sendChat(Konsultasi konsultasi, Map<String, String> chat) {
        addDisposable(cache.send(chat)
                .subscribe(response -> addDisposable(cache.save(response.body().getResult()).subscribe(resp -> {
                    KonsultasiViewState viewState = getViewState().getValue();
                    viewState.addKonsultasi(konsultasi);
                    getViewState().setValue(viewState);
                }))));
    }

    void getChat(Map<String, String> chat) {
        addDisposable(cache.getChatKonsultasi(chat)
                .subscribe(response -> {
                    KonsultasiViewState viewState = getViewState().getValue();
                    if (response.code() == 200 && response.body() != null) {
                        viewState.setKonsultasis(response.body().getResult().getListChat());
                    } else {
                        viewState.setError("Terjadi kesalahan saat mengirim chat");
                    }
                    getViewState().setValue(viewState);
                }));
    }
}
