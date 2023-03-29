package id.comrade.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.utils.SharedPreferenceUtil;

public abstract class BaseActivity<U extends BaseViewState,
        T extends BaseViewModel<U>> extends AppCompatActivity {
    private SharedPreferenceUtil sharedPreferences;

    private T viewModel;

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("ConstantConditions")
    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        sharedPreferences = SharedPreferenceUtil.getInstance(this);

        ButterKnife.bind(this);

        setSupportActionBar(getToolbar());
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            TextView tvTitle = getToolbar().findViewById(R.id.toolbar_title);
            if (tvTitle != null) {
                tvTitle.setText(getToolbarTitle());
            }

            ab.setTitle("");
            ab.setDisplayHomeAsUpEnabled(hasParent());
        }
    }

    protected abstract void handleViewState(U viewState);

    protected void putSharedPreferences(String key, String value) {
        sharedPreferences.put(key, value);
    }

    public String getSharedPreferences(String key) {
        return sharedPreferences.get(key);
    }

    protected void clearSharedPreferences() {
        sharedPreferences.clear();
    }

    protected void show(Class cls) {
        show(cls, new int[]{});
    }

    protected void show(Class cls, int[] flags) {
        Intent intent = new Intent(this, cls);
        for(int flag : flags) {
            intent.addFlags(flag);
        }
        startActivity(intent);
    }

    protected T getViewModel() {
        return viewModel;
    }

    protected void init(Class<T> cls) {
        viewModel = ViewModelProviders.of(this).get(cls);
        viewModel.getViewState().observe(this, this::handleViewState);
    }

    protected String getToolbarTitle() {
        return "";
    }

    protected boolean hasParent() {
        return true;
    }

    protected Toolbar getToolbar() {
        return null;
    }

    protected abstract int getContentView();

    protected static class StubHolder {
        protected StubHolder(ViewStub stub, @LayoutRes int layoutId) {
            stub.setLayoutResource(layoutId);
            View view = stub.inflate();
            ButterKnife.bind(this, view);
        }
    }
}
