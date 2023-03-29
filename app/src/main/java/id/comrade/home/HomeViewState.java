package id.comrade.home;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Posting;
import id.comrade.model.Reminder;
import id.comrade.model.Topic;

public class HomeViewState extends BaseViewState {
    private List<Topic> topics;
    private List<Posting> news;
    private Reminder reminder;

    HomeViewState() {
        setLoading(true);
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
        checkLoading();
    }

    public List<Posting> getNews() {
        return news;
    }

    public void setNews(List<Posting> news) {
        this.news = news;
        checkLoading();
    }

    private void checkLoading() {
        if (news != null && topics != null) {
            setLoading(false);
        }
    }
}
