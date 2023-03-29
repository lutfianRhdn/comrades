package id.comrade.register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.model.User;
import id.comrade.registertype.RegisterTypeActivity;
import id.comrade.utils.Gender;
import id.comrade.utils.SharedPreferenceUtil;

public class RegisterActivity extends BaseActivity<RegisterViewState, RegisterViewModel>
        implements DatePickerDialog.OnDateSetListener {

    private static final int MIN_PASSWORD = 6;

    @BindView(R.id.et_activity_register_name)
    EditText etName;
    @BindView(R.id.et_activity_register_email)
    EditText etEmail;
    @BindView(R.id.et_activity_register_password)
    EditText etPassword;
    @BindView(R.id.et_activity_register_birth)
    EditText etBirth;
    @BindView(R.id.et_activity_register_phone)
    EditText etPhone;

    @BindView(R.id.til_activity_register_birth)
    TextInputLayout mTilBirth;

    @BindView(R.id.spinner_activity_register_phone_prefix)
    Spinner spinnerPhoneCode;

    @BindView(R.id.rg_activity_register_gender)
    RadioGroup rgGender;

    @BindView(R.id.toolbar_activity_register)
    Toolbar toolbar;

    @BindView(R.id.btn_activity_register_next)
    Button btnNext;

    private DatePickerDialog datePickerDialog;

    private ArrayAdapter<String> spinnerAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(RegisterViewModel.class);

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setOnCancelListener(dialogInterface -> closeKeyboard());

        etBirth.setOnTouchListener((v, evt) -> {
            if (evt.getAction() == MotionEvent.ACTION_DOWN) {
                datePickerDialog.show();
                return false;
            }
            return onTouchEvent(evt);
        });

        spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.dial_code)
        );
        spinnerPhoneCode.setAdapter(spinnerAdapter);

        btnNext.setOnClickListener(v -> showRegisterTypeActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datePickerDialog = null;
        spinnerAdapter = null;
    }

    @OnFocusChange(R.id.et_activity_register_email)
    void onEmailFocusChanged(boolean hasFocus) {
        if (!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find()) {
            etEmail.setError(getString(R.string.email_validation));
        } else if (!hasFocus && Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find()) {
            etEmail.setError(null);
        }
    }

    @OnFocusChange(R.id.et_activity_register_password)
    void onPasswordFocusChanged(boolean hasFocus) {
        if (!hasFocus && etPassword.getText().toString().length() < MIN_PASSWORD) {
            etPassword.setError(getString(R.string.password_validation));
        } else if (!hasFocus) {
            etPassword.setError(null);
        }
    }

    @OnTextChanged({R.id.et_activity_register_name, R.id.et_activity_register_email,
            R.id.et_activity_register_birth, R.id.et_activity_register_phone,
            R.id.et_activity_register_password})
    void onInputChanged() {
        if (etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() ||
                etBirth.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).find() ||
                etPassword.getText().toString().isEmpty() ||
                etPassword.getText().toString().length() < MIN_PASSWORD) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        etBirth.setText(String.format(Locale.getDefault(), "%02d-%02d-%d",
                dayOfMonth, month, year));
        closeKeyboard();
    }

    @Override
    protected void handleViewState(RegisterViewState viewState) {
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.register_label);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    private void showRegisterTypeActivity() {
        Intent intent = new Intent(this, RegisterTypeActivity.class)
                .putExtra(RegisterTypeActivity.REGISTER_EXTRA, getRegisterExtra());
        startActivity(intent);
    }

    private User getRegisterExtra() {
        return new User() {{
            setEmail(etEmail.getText().toString());
            setNama(etName.getText().toString());
            setTanggalLahir(etBirth.getText().toString());
            setPassword(etPassword.getText().toString());
            setTelp(etPhone.getText().toString());
            setEmail(etEmail.getText().toString());
            setJk(getGender(rgGender.getCheckedRadioButtonId()));
            setTokenFcm(getSharedPreferences(SharedPreferenceUtil.PREF_FCM_TOKEN));
        }};
    }

    private String getGender(int checkedRadioButtonId) {
        return checkedRadioButtonId == R.id.rb_activity_register_male ? Gender.MALE : Gender.FEMALE;
    }
}
