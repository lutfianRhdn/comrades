package id.comrade.news.latest;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Posting;

class LatestNewsViewState extends BaseViewState {
    private List<Posting> news;

    public List<Posting> getNews() {
        return news;
    }

    public void setNews(List<Posting> news) {
        this.news = news;
    }
}
