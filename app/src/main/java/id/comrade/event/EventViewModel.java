package id.comrade.event;

import android.app.Application;

import id.comrade.base.BaseViewModel;

public class EventViewModel extends BaseViewModel<EventViewState> {
    public EventViewModel(Application app) {
        super(app);

        EventViewState viewState = new EventViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    void finishLoad() {
        EventViewState viewState = getViewState().getValue();
        viewState.setLoading(false);
        getViewState().setValue(viewState);
    }
}
