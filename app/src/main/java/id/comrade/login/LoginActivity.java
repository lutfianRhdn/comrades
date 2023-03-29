package id.comrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.main.MainActivity;
import id.comrade.model.User;
import id.comrade.register.RegisterActivity;
import id.comrade.utils.Security;
import id.comrade.utils.SharedPreferenceUtil;

public class LoginActivity extends BaseActivity<LoginViewState, LoginViewModel> {
    private static final int RC_SIGN_IN = 0;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_activity_login_email)
    EditText etEmail;
    @BindView(R.id.et_activity_login_password)
    EditText etPassword;

    @BindView(R.id.btn_activity_login_login)
    Button btnLogin;
    @BindView(R.id.btn_activity_login_google_login)
    Button btnGoogleLogin;

    @BindView(R.id.pb_activity_login)
    ProgressBar pbLogin;

    @BindView(R.id.tv_activity_login_register)
    TextView tvRegister;
    @BindView(R.id.tv_activity_login_forgot_password)
    TextView tvForgotPassword;

    private String privateKey;

    private GoogleSignInClient googleSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(LoginViewModel.class);

        tvForgotPassword.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
        tvRegister.setOnClickListener(v -> show(RegisterActivity.class));

        btnLogin.setOnClickListener(v -> login());
        btnGoogleLogin.setOnClickListener(v -> loginWithGoogle());

        googleSignIn = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Log.i(TAG, "Nilainya null bangsat lah anjing");
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInGoogle(task);
        }
    }

    @OnFocusChange(R.id.et_activity_login_email)
    void onEmailFocusChanged(View v, boolean hasFocus) {
        if (!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find()) {
            etEmail.setError(getString(R.string.email_validation));
            btnLogin.setEnabled(false);
        } else if (!hasFocus && Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find()) {
            etEmail.setError(null);
            btnLogin.setEnabled(true);
        }
    }

    @OnTextChanged({R.id.et_activity_login_email, R.id.et_activity_login_password})
    void onInputChanged() {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find()) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }

    @Override
    protected void handleViewState(LoginViewState viewState) {
        if (viewState != null) {
            boolean loading = viewState.isLoading();
            btnLogin.setEnabled(!loading);
            pbLogin.setVisibility(loading ? View.VISIBLE : View.GONE);
            if (loading) btnLogin.setText("");
            else btnLogin.setText(R.string.signin_label);

            User user = viewState.getLoginResponse();
            if (user != null) handleLogin(user, loading);

            String msg = viewState.getError();
            if (msg != null) showToast(msg);
        }
    }

    private void handleLogin(User user, boolean loading) {
        if (loading) {
            privateKey = user.getPrivateKey();

            int userId = user.getIdUser();

            putSharedPreferences(SharedPreferenceUtil.PREF_HAS_LOGIN, "true");
            putSharedPreferences(User.PREF_USER_ID, String.valueOf(userId));
            putSharedPreferences(User.PREF_USER_NAME, decrypt(user.getNama()));
            putSharedPreferences(User.PREF_USER_EMAIL, decrypt(user.getEmail()));
            putSharedPreferences(User.PREF_USER_GENDER, user.getJk());
            putSharedPreferences(User.PREF_USER_PHONE, decrypt(user.getTelp()));
            putSharedPreferences(User.PREF_USER_PHOTO, user.getFoto());
            putSharedPreferences(User.PREF_USER_TYPE, user.getJenisUser());
            putSharedPreferences(User.PREF_USER_BIRTH, decrypt(user.getTanggalLahir()));

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                putSharedPreferences(User.PREF_USER_EMAIL, acct.getEmail());
                putSharedPreferences(User.PREF_USER_PHOTO, acct.getPhotoUrl().toString());
            }

            getViewModel().migrateReminder(userId);
            return;
        }

        getViewModel().clear();
        show(MainActivity.class);
        finish();
    }

    private void handleSignInGoogle(Task<GoogleSignInAccount> task) {
        try {
            final GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account != null) getViewModel().login(account.getEmail(), "",
                            getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN), "google");
        } catch (ApiException ex) {
            ex.printStackTrace();
            showToast(getString(R.string.google_sign_in_failed));
        }
    }

    private void login() {
        getViewModel().login(
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN)
        );
    }

    private void loginWithGoogle() {
        startActivityForResult(googleSignIn.getSignInIntent(), RC_SIGN_IN);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    private String decrypt(String attributeToDecrypt) {
        return Security.decrypt(privateKey, attributeToDecrypt);
    }
}
