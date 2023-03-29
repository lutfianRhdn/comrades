package id.comrade.model;

import java.util.List;

public class ListKonsultasiResponse {
    private User user;
    private List<Konsultasi> listChatSahabatOdha;

    public ListKonsultasiResponse(User user, List<Konsultasi> listChatSahabatOdha) {
        this.user = user;
        this.listChatSahabatOdha = listChatSahabatOdha;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Konsultasi> getListChatSahabatOdha() {
        return listChatSahabatOdha;
    }

    public void setListChatSahabatOdha(List<Konsultasi> listChatSahabatOdha) {
        this.listChatSahabatOdha = listChatSahabatOdha;
    }
}
