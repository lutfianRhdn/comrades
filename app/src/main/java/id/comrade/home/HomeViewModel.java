package id.comrade.home;

import android.app.Application;

import id.comrade.base.BaseViewModel;
import id.comrade.model.Reminder;
import id.comrade.repositories.NewsCache;
import id.comrade.repositories.ReminderCache;
import id.comrade.repositories.TopikCache;
import id.comrade.utils.DateUtils;

public class HomeViewModel extends BaseViewModel<HomeViewState> {
    private TopikCache topicCache = TopikCache.getInstance();
    private NewsCache newsCache = NewsCache.getInstance();
    private ReminderCache reminderCache;

    public HomeViewModel(Application app) {
        super(app);

        reminderCache = ReminderCache.getInstance(app);

        HomeViewState viewState = new HomeViewState();
        getViewState().setValue(viewState);
        getNews(viewState);
        getTopics(viewState);
        getReminders(viewState);
    }

    private void getReminders(HomeViewState viewState) {
        addDisposable(reminderCache.getCache()
                .subscribe(reminders -> {
                    Reminder reminder = DateUtils.findClosest(reminders);
                    checkLoading(viewState);
                    viewState.setReminder(reminder);
                    getViewState().setValue(viewState);
                }));
    }

    private void getTopics(HomeViewState viewState) {
        addDisposable(topicCache.getCache()
                .subscribe(topics -> {
                    viewState.setTopics(topics);
                    viewState.setError(null);
                    checkLoading(viewState);
                    getViewState().setValue(viewState);
                }, error -> {
                    viewState.setNews(null);
                    viewState.setError(error.getMessage());
                    viewState.setLoading(false);
                    getViewState().setValue(viewState);
                }));
    }

    private void getNews(HomeViewState viewState) {
        addDisposable(newsCache.getCache()
                .subscribe(news -> {
                    viewState.setNews(news);
                    viewState.setError(null);
                    checkLoading(viewState);
                    getViewState().setValue(viewState);
                }, error -> {
                    viewState.setNews(null);
                    viewState.setError(error.getMessage());
                    viewState.setLoading(false);
                    getViewState().setValue(viewState);
                }));
    }

    private void checkLoading(HomeViewState viewState) {
        if (viewState.getNews() != null && viewState.getTopics() != null) {
            viewState.setLoading(false);
        }
    }
}
