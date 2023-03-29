package id.comrade.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel<T extends BaseViewState> extends AndroidViewModel {
    private CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<T> viewState = new MutableLiveData<>();

    public BaseViewModel(Application app) {
        super(app);
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public MutableLiveData<T> getViewState() {
        return viewState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
        disposables = null;
    }
}
