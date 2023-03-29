package id.comrade.reminder.edit;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.main.MainActivity;
import id.comrade.model.Obat;
import id.comrade.model.Reminder;
import id.comrade.reminder.ReminderSpinnerAdapter;

public class EditReminderActivity extends BaseActivity<EditReminderViewState,
        EditReminderViewModel> {
    public static final String REMINDER_EXTRA = "intent-extra:reminder";

    @BindView(R.id.pb_activity_edit_reminder_progress)
    ProgressBar pbProgress;

    @BindView(R.id.btn_activity_edit_reminder_save)
    Button btnSave;
    @BindView(R.id.btn_activity_edit_reminder_delete)
    Button btnDelete;

    @BindView(R.id.vs_activity_edit_reminder_form)
    ViewStub vsForm;

    @BindView(R.id.toolbar_activity_edit_reminder)
    Toolbar toolbar;

    private Reminder reminder;

    private FormStubHolder formStubHolder;
    private ReminderSpinnerAdapter spinnerAdapter;

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(EditReminderViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            showToast(getString(R.string.label_error));
            show(MainActivity.class);
        }

        reminder = bundle.getParcelable(REMINDER_EXTRA);
        getViewModel().filter(reminder);

        btnSave.setOnClickListener(v -> saveReminder());
        btnDelete.setOnClickListener(v -> showAlert());

        formStubHolder = new FormStubHolder(vsForm, R.layout.form_reminder);

        spinnerAdapter = new ReminderSpinnerAdapter(this);
        formStubHolder.spinnerJenisArv.setAdapter(spinnerAdapter);

        formStubHolder.etStartingTime.setText(reminder.getStartingTime().substring(0, 5));

        if (reminder.getReminder().equals("8")) {
            formStubHolder.rbEight.setChecked(true);
        } else {
            formStubHolder.rbTwelve.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spinnerAdapter = null;
    }

    @Override
    protected void handleViewState(EditReminderViewState viewState) {
        handleLoading(viewState.isLoading());
        handleMessage(viewState.getMessage());
        handleObat(viewState.getObats());
        handleError(viewState.getError());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_reminder;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.label_edit_reminder);
    }

    @Override
    protected boolean hasParent() {
        return true;
    }

    private void handleError(String error) {
        if (error != null) {
            showToast(error);
        }
    }

    private void handleLoading(boolean loading) {
        if (loading) {
            pbProgress.setVisibility(View.VISIBLE);
            btnSave.setEnabled(false);
            btnSave.setText("");
        } else {
            pbProgress.setVisibility(View.GONE);
            btnSave.setEnabled(true);
            btnSave.setText(getString(R.string.save_label));
        }
    }

    private void handleMessage(String msg) {
        if (msg != null) {
            showToast(msg);
            show(MainActivity.class);
        }
    }

    private void handleObat(List<Obat> obats) {
        if (obats != null) {
            int selectedPosition = -1;
            for (int i = 0; i < obats.size(); i++) {
                Obat obat = obats.get(i);
                String namaObat = obat.getMerek();

                if (reminder.getNamaObat().equals(namaObat)) {
                    selectedPosition = i;
                    break;
                }
            }

            spinnerAdapter.setObat(obats);
            formStubHolder.spinnerJenisArv.setSelection(selectedPosition);
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_verification)
                .setMessage(R.string.delete_message)
                .setPositiveButton(R.string.alert_positive_label, (dialogInterface, i) -> delete())
                .setNegativeButton(R.string.alert_negative_label, ((dialogInterface, i) ->
                        dialogInterface.cancel()))
                .create()
                .show();
    }

    private void delete() {
        getViewModel().delete(reminder);
    }

    private void saveReminder() {
        String namaObat = formStubHolder.getSpinnerSelected();
        String timeReminder = formStubHolder.getCheckedReminder();
        String startingTime = formStubHolder.getStartingTime();

        getViewModel().deleteReminder(reminder);
        reminder.setNamaObat(namaObat);
        reminder.setReminder(timeReminder);
        reminder.setStartingTime(startingTime);
        getViewModel().saveReminder(reminder);
    }

    class FormStubHolder extends BaseActivity.StubHolder {
        private static final String INVALID_CHECKED = "-1";

        @BindView(R.id.spinner_form_reminder_jenis_arv)
        Spinner spinnerJenisArv;

        @BindView(R.id.rg_form_reminder_reminder)
        RadioGroup rgReminder;

        @BindView(R.id.et_form_reminder_starting_time)
        EditText etStartingTime;

        @BindView(R.id.rb_form_reminder_eight_hours)
        RadioButton rbEight;
        @BindView(R.id.rb_form_reminder_twelve_hours)
        RadioButton rbTwelve;

        FormStubHolder(ViewStub stub, @LayoutRes int layoutId) {
            super(stub, layoutId);
        }

        String getSpinnerSelected() {
            Obat obat = (Obat) spinnerJenisArv.getSelectedItem();
            return obat.getMerek();
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
