package id.comrade.editprofile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.main.MainActivity;
import id.comrade.model.User;
import id.comrade.utils.Gender;

public class EditProfileActivity extends BaseActivity<EditProfileViewState, EditProfileViewModel>
implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.rg_activity_edit_profile_gender)
    RadioGroup rgUserGender;

    @BindView(R.id.rb_activity_edit_profile_male)
    RadioButton rbUserMale;
    @BindView(R.id.rb_activity_edit_profile_female)
    RadioButton rbUserFemale;

    @BindView(R.id.et_activity_edit_profile_name)
    EditText etUserName;
    @BindView(R.id.et_activity_edit_profile_birth)
    EditText etUserBirth;
    @BindView(R.id.et_activity_edit_profile_phone)
    EditText etUserPhone;

    @BindView(R.id.btn_activity_edit_profile_save)
    Button btnSave;
    @BindView(R.id.btn_activity_edit_profile_cancel)
    Button btnCancel;

    @BindView(R.id.pb_activity_edit_profile)
    ProgressBar pbProgress;

    @BindView(R.id.spinner_activity_edit_profile_phone_prefix)
    Spinner spinnerPhoneCode;

    @BindView(R.id.toolbar_activity_edit_profile)
    Toolbar toolbar;

    private DatePickerDialog datePickerDialog;

    private ArrayAdapter<String> spinnerAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(EditProfileViewModel.class);

        initView();

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setOnCancelListener(dialogInterface -> closeKeyboard());

        etUserBirth.setOnTouchListener((v, evt) -> {
            if (evt.getAction() == MotionEvent.ACTION_DOWN) {
                datePickerDialog.show();
                return false;
            }
            return onTouchEvent(evt);
        });

        btnSave.setOnClickListener(view -> save());
        btnCancel.setOnClickListener(view -> onBackPressed());

        spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.dial_code)
        );
        spinnerPhoneCode.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        datePickerDialog = null;
        spinnerAdapter = null;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_edit_profile);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    @Override
    protected void handleViewState(EditProfileViewState viewState) {
        handleLoading(viewState.isLoading());
        handleMessage(viewState.getMessage(), viewState.getUser());
        handleError(viewState.getError());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etUserBirth.setText(String.format(Locale.getDefault(), "%02d-%02d-%d",
                dayOfMonth, month, year));
        closeKeyboard();
    }

    @OnTextChanged({R.id.et_activity_edit_profile_name, R.id.et_activity_edit_profile_birth,
            R.id.et_activity_edit_profile_phone})
    void onInputChanged() {
        if (etUserName.getText().toString().isEmpty() || etUserBirth.getText().toString().isEmpty()
                || etUserPhone.getText().toString().isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
    }

    private void initView() {
        String userName = getSharedPreferences(User.PREF_USER_NAME);
        String userBirth = getSharedPreferences(User.PREF_USER_BIRTH);
        String userGender = getSharedPreferences(User.PREF_USER_GENDER);
        String userPhone = getSharedPreferences(User.PREF_USER_PHONE);

        etUserName.setText(userName);
        etUserBirth.setText(userBirth);
        etUserPhone.setText(userPhone);

        if (userGender.equals(Gender.FEMALE)) {
            rbUserFemale.setChecked(true);
        }
    }

    private void save() {
        String userName = etUserName.getText().toString();
        String userBirth = etUserBirth.getText().toString();
        String userPhone = etUserPhone.getText().toString();

        int selectedGender = rgUserGender.getCheckedRadioButtonId();
        String gender = selectedGender == R.id.rb_activity_edit_profile_male ?
                Gender.MALE : Gender.FEMALE;

        User user = new User();
        user.setIdUser(Integer.valueOf(getSharedPreferences(User.PREF_USER_ID)));
        user.setNama(userName);
        user.setTanggalLahir(userBirth);
        user.setJk(gender);
        user.setTelp(userPhone);

        getViewModel().save(user);
    }

    private void handleLoading(boolean loading) {
        if (loading) {
            btnSave.setEnabled(false);
            btnSave.setText("");
            pbProgress.setVisibility(View.VISIBLE);
        } else {
            btnSave.setEnabled(true);
            btnSave.setText(getString(R.string.save_label));
            pbProgress.setVisibility(View.GONE);
        }
    }

    private void handleMessage(String message, User user) {
        if (message != null) {
            showToast(message);

            putSharedPreferences(User.PREF_USER_NAME, user.getNama());
            putSharedPreferences(User.PREF_USER_GENDER, user.getJk());
            putSharedPreferences(User.PREF_USER_PHONE, user.getTelp());
            putSharedPreferences(User.PREF_USER_BIRTH, user.getTanggalLahir());

            show(MainActivity.class);
            finish();
        }
    }

    private void handleError(String error) {
        if (error != null) {
            showToast(error);
        }
    }

}
