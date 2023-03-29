package id.comrade.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import id.comrade.utils.SharedPreferenceUtil;

public abstract class BaseFragment<U extends BaseViewState,
        T extends BaseViewModel<U>> extends Fragment {
    private SharedPreferenceUtil sharedPreferences;

    private T viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        sharedPreferences = SharedPreferenceUtil.getInstance(getContext());
    }

    protected void show(Class cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent, getExtras());
    }

    protected void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void init(Class<T> cls) {
        viewModel = ViewModelProviders.of(this).get(cls);
        viewModel.getViewState().observe(this, this::handleState);
    }

    protected String getSharedPreferences(String key) {
        return sharedPreferences.get(key);
    }

    protected T getViewModel() {
        return viewModel;
    }

    protected Bundle getExtras() {
        return null;
    }

    protected abstract void handleState(U viewState);

    @LayoutRes
    protected abstract int getContentView();
}
