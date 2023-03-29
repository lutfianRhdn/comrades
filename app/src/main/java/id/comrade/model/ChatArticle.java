package id.comrade.model;

import java.util.List;

public class ChatArticle extends Chat {
    private List<Article> mArticle;

    public ChatArticle(String message, String date, String time, int status, String uidSender,
                       String uidRecevier, List<Article> mArticle) {
        super(message, date, time, status, uidSender, uidRecevier);
        this.mArticle = mArticle;
    }


    public List<Article> getArticle() {
        return mArticle;
    }

    public void setArticle(List<Article> mArticle) {
        this.mArticle = mArticle;
    }

}
