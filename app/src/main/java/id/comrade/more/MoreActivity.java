package id.comrade.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.editprofile.EditProfileActivity;
import id.comrade.login.LoginActivity;
import id.comrade.main.MainActivity;
import id.comrade.utils.SharedPreferenceUtil;

public class MoreActivity extends BaseActivity<MoreViewState, MoreViewModel> {

    @BindView(R.id.fl_activity_more_edit_profile)
    FrameLayout flEditProfile;
    @BindView(R.id.fl_activity_more_settings)
    FrameLayout flSettings;
    @BindView(R.id.fl_activity_more_help)
    FrameLayout flHelp;
    @BindView(R.id.fl_activity_more_term)
    FrameLayout flTerms;
    @BindView(R.id.fl_activity_more_about)
    FrameLayout flAbout;
    @BindView(R.id.fl_activity_more_share)
    FrameLayout flShare;
    @BindView(R.id.fl_activity_more_logout)
    FrameLayout flLogout;

    @BindView(R.id.toolbar_activity_more)
    Toolbar toolbar;

    private GoogleSignInClient googleSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(MoreViewModel.class);

        flEditProfile.setOnClickListener(v -> show(EditProfileActivity.class));
        flSettings.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        flHelp.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        flTerms.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        flAbout.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        flShare.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        flLogout.setOnClickListener(v -> getViewModel().logout());

        googleSignIn = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build());
    }

    @Override
    protected void handleViewState(MoreViewState viewState) {
        handleLogout(viewState.isLogout());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_more;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_more);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    private void handleLogout(boolean logout) {
        if (logout) {
            clearSharedPreferences();
            googleSignIn.signOut();

            finish();
            show(MainActivity.class, new int[]{ Intent.FLAG_ACTIVITY_CLEAR_TOP });
        }
    }
}
