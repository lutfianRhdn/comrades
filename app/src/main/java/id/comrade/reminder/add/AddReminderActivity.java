package id.comrade.reminder.add;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.main.MainActivity;
import id.comrade.model.Obat;
import id.comrade.model.Reminder;
import id.comrade.model.User;
import id.comrade.reminder.ReminderSpinnerAdapter;

public class AddReminderActivity extends BaseActivity<AddReminderViewState, AddReminderViewModel>
        implements TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.toolbar_activity_add_reminder)
    Toolbar toolbar;

    @BindView(R.id.btn_activity_add_reminder_save)
    Button btnSave;
    @BindView(R.id.btn_activity_add_reminder_cancel)
    Button btnCancel;

    @BindView(R.id.pb_activity_add_reminder)
    ProgressBar pbSave;

    @BindView(R.id.vs_activity_add_reminder_form)
    ViewStub vsForm;

    private TimePickerDialog timePickerDialog;

    private FormStubHolder formStubHolder;
    private ReminderSpinnerAdapter spinnerAdapter;

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(AddReminderViewModel.class);

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(
                this,
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setOnCancelListener(d -> closeKeyboard());

        btnSave.setOnClickListener(v -> saveReminder());
        btnCancel.setOnClickListener(v -> onBackPressed());

        formStubHolder = new FormStubHolder(vsForm, R.layout.form_reminder);

        spinnerAdapter = new ReminderSpinnerAdapter(this);
        formStubHolder.spinnerJenisArv.setAdapter(spinnerAdapter);

        formStubHolder.etStartingTime.setOnTouchListener((v, evt) -> {
            if (evt.getAction() == MotionEvent.ACTION_DOWN) {
                timePickerDialog.show();
                return false;
            }
            return onTouchEvent(evt);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spinnerAdapter = null;
        timePickerDialog = null;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        formStubHolder.etStartingTime.setText(String.format(Locale.getDefault(), "%02d:%02d",
                hourOfDay, minute));
        closeKeyboard();
    }

    private void saveReminder() {
        Obat obat = formStubHolder.getSpinnerSelected();

        String namaObat = obat.getMerek();
        String reminder = formStubHolder.getCheckedReminder();
        String startingTime = formStubHolder.getStartingTime();

        Reminder newReminder = new Reminder(namaObat, reminder, startingTime);
        newReminder.setIdObat(obat.getIdObat());
        newReminder.setIdUser(Integer.valueOf(getSharedPreferences(User.PREF_USER_ID)));
        getViewModel().saveReminder(newReminder);
    }

    @Override
    protected void handleViewState(AddReminderViewState viewState) {
        handleLoading(viewState.isLoading());
        handleFinish(viewState.isFinish());
        handleObat(viewState.getObats());
        handleError(viewState.getError());
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_set_reminder);
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
    protected int getContentView() {
        return R.layout.activity_add_reminder;
    }

    private void handleError(String error) {
        if (error != null) {
            showToast(error);
        }
    }

    private void handleFinish(boolean finish) {
        if (finish) {
            showToast(getString(R.string.success_add_reminder));
            show(MainActivity.class);
        }
    }

    private void handleLoading(boolean isLoading) {
        if (isLoading) {
            btnSave.setEnabled(false);
            btnSave.setText("");
            pbSave.setVisibility(View.VISIBLE);
        } else {
            btnSave.setEnabled(true);
            btnSave.setText(R.string.save_label);
            pbSave.setVisibility(View.GONE);
        }
    }

    private void handleObat(List<Obat> obats) {
        if (obats != null) {
            spinnerAdapter.setObat(obats);
        }
    }

    class FormStubHolder extends BaseActivity.StubHolder {
        private static final String INVALID_CHECKED = "-1";

        @BindView(R.id.spinner_form_reminder_jenis_arv)
        Spinner spinnerJenisArv;

        @BindView(R.id.rg_form_reminder_reminder)
        RadioGroup rgReminder;

        @BindView(R.id.et_form_reminder_starting_time)
        EditText etStartingTime;

        FormStubHolder(ViewStub stub, @LayoutRes int layoutId) {
            super(stub, layoutId);
        }

        Obat getSpinnerSelected() {
            return (Obat) spinnerJenisArv.getSelectedItem();
        }

        String getCheckedReminder() {
            int checkedRadioId = rgReminder.getCheckedRadioButtonId();
            switch (checkedRadioId) {
                case R.id.rb_form_reminder_eight_hours:
                    return "8";
                case R.id.rb_form_reminder_twelve_hours:
                    return "12";
                default:
                    return INVALID_CHECKED;
            }
        }

        String getStartingTime() {
            return etStartingTime.getText().toString();
        }
    }
}
