package id.comrade.registertype;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.login.LoginActivity;
import id.comrade.model.User;

public class RegisterTypeActivity extends BaseActivity<RegisterTypeViewState, RegisterTypeViewModel> {
    public static final String REGISTER_EXTRA = "intent-extra:register";

    @BindView(R.id.tv_register_as)
    TextView mTvRegisterAs;

    @BindView(R.id.btn_activity_register_type_user)
    Button mBtnRegisterUser;
    @BindView(R.id.btn_activity_register_type_friend)
    Button mBtnRegisterFriend;

    @BindView(R.id.pb_activity_register_type_user)
    ProgressBar pbRegisterUser;
    @BindView(R.id.pb_activity_register_type_friend)
    ProgressBar pbRegisterFriend;

    @BindView(R.id.toolbar_activity_register_type)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(RegisterTypeViewModel.class);

        initializeSpannedTextView();

        mBtnRegisterFriend.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        mBtnRegisterUser.setOnClickListener(v -> registerAsUser());
    }

    @Override
    protected void handleViewState(RegisterTypeViewState viewState) {
        handleLoading(viewState.isLoading(), viewState.getRegisterProcess());
        handleError(viewState.getError());
        handleMessage(viewState.getMessage());
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.register_label);
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register_type;
    }

    private void initializeSpannedTextView() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        int start;

        spannableString.append(getString(R.string.register_as_user_label));

        start = spannableString.length();
        spannableString.append(getString(R.string.user_label))
                .setSpan(boldStyle, start, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.append(getString(R.string.or_label));

        start = spannableString.length();
        spannableString.append(getString(R.string.friend_to_share_label))
                .setSpan(boldStyle, start, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.append('?');
        mTvRegisterAs.setText(spannableString);
    }

    @SuppressWarnings("unchecked")
    private void registerAsUser() {
        User body = getIntent().getParcelableExtra(REGISTER_EXTRA);
        getViewModel().registerAsUser(body);
    }


    private void handleMessage(String message) {
        if (message == null) {
            return;
        }

        showToast(message);
        show(LoginActivity.class);
        finish();
    }

    private void handleError(String error) {
        if (error != null) {
            showToast(error);
        }
    }

    private void handleLoading(boolean loading, int registerProcess) {
        mBtnRegisterFriend.setEnabled(!loading);
        mBtnRegisterUser.setEnabled(!loading);

        if (loading) {
            if (registerProcess == RegisterTypeViewState.PROCESS_USER) {
                mBtnRegisterUser.setText("");
                pbRegisterUser.setVisibility(View.VISIBLE);
            } else {
                mBtnRegisterFriend.setText("");
                pbRegisterFriend.setVisibility(View.VISIBLE);
            }
        } else {
            if (registerProcess == RegisterTypeViewState.PROCESS_USER) {
                mBtnRegisterUser.setText(getString(R.string.register_as_user_label));
                pbRegisterUser.setVisibility(View.GONE);
            } else {
                mBtnRegisterFriend.setText(getString(R.string.register_as_friend_label));
                pbRegisterFriend.setVisibility(View.GONE);
            }
        }
    }
}
