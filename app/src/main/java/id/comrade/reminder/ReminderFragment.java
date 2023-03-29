package id.comrade.reminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseFragment;
import id.comrade.main.MainActivity;
import id.comrade.model.Reminder;
import id.comrade.reminder.add.AddReminderActivity;

public class ReminderFragment extends BaseFragment<ReminderViewState, ReminderViewModel> {

    @BindView(R.id.cl_fragment_reminder_main_view)
    ConstraintLayout clMainView;

    @BindView(R.id.pb_fragment_reminder_progress)
    ProgressBar pbProgress;

    @BindView(R.id.rv_fragment_reminder)
    RecyclerView rvReminders;

    @BindView(R.id.btn_fragment_reminder_add)
    Button btnAddReminder;

    private ReminderAdapter adapter;
    private ReminderAdapter.OnDeleteMenuListener onDeleteMenuListener;

    public static ReminderFragment newInstance() {
        return new ReminderFragment();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(ReminderViewModel.class);

        onDeleteMenuListener = reminder -> getViewModel().delete(reminder);
        adapter = new ReminderAdapter(getContext(), onDeleteMenuListener);
        rvReminders.setAdapter(adapter);

        btnAddReminder.setOnClickListener(v -> show(AddReminderActivity.class));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.getSelectedReminderName() != null) {
            adapter.setSelectedReminder(mainActivity.getSelectedReminderName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        onDeleteMenuListener = null;
    }

    @Override
    protected void handleState(ReminderViewState viewState) {
        handleLoading(viewState.isLoading());
        handleReminders(viewState.getReminders());
        handleMessage(viewState.getMessage());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_reminder;
    }

    private void handleMessage(String msg) {
        if (msg != null) {
            showToast(msg);
        }
    }

    private void handleLoading(boolean loading) {
        if (loading) {
            pbProgress.setVisibility(View.VISIBLE);
            clMainView.setVisibility(View.GONE);
        } else {
            pbProgress.setVisibility(View.GONE);
            clMainView.setVisibility(View.VISIBLE);
        }
    }

    private void handleReminders(List<Reminder> reminders) {
        adapter.setReminders(reminders);
    }
}
