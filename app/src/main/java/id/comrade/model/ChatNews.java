package id.comrade.model;

import java.util.List;

public class ChatNews extends Chat {
    private List<Posting> mNews;

    public ChatNews(String message, String date, String time, int status, String uidSender,
                    String uidRecevier, List<Posting> mNews) {
        super(message, date, time, status, uidSender, uidRecevier);
        this.mNews = mNews;
    }

    public List<Posting> getNews() {
        return mNews;
    }

    public void setNews(List<Posting> mNews) {
        this.mNews = mNews;
    }
}
