package id.comrade.reminder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.model.Reminder;
import id.comrade.reminder.edit.EditReminderActivity;

public class ReminderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ORDINARY_ITEM = 0;
    private static final int SELECTED_ITEM = 1;

    private List<Reminder> reminders;

    private Context context;

    private OnDeleteMenuListener listener;

    private String selectedReminderName;

    ReminderAdapter(Context context, OnDeleteMenuListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Reminder reminder = reminders.get(position);
        return reminder.getNamaObat().equals(selectedReminderName) ? SELECTED_ITEM : ORDINARY_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ORDINARY_ITEM:
                return new OrdinaryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_reminder, parent, false));
            default:
                return new SelectedViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_reminder_active, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        if (holder instanceof OrdinaryViewHolder) {
            OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
            ordinaryViewHolder.tvMedicineName.setText(reminder.getNamaObat());
            ordinaryViewHolder.tvReminderTime.setText(getReminderTime(reminder.getStartingTime(),
                    Integer.valueOf(reminder.getReminder())));
            ordinaryViewHolder.ivMenu.setOnClickListener(v -> showPopup(v, reminder));
        } else if (holder instanceof SelectedViewHolder) {
            SelectedViewHolder selectedViewHolder = (SelectedViewHolder) holder;
            selectedViewHolder.tvMedicineName.setText(reminder.getNamaObat());
            selectedViewHolder.tvReminderTime.setText(getReminderTime(reminder.getStartingTime(),
                    Integer.valueOf(reminder.getReminder())));
            selectedViewHolder.ivMenu.setOnClickListener(v -> showPopup(v, reminder));
        }
    }

    @Override
    public int getItemCount() {
        return reminders == null ? 0 : reminders.size();
    }

    private void showAlert(Reminder reminder) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_verification)
                .setMessage(R.string.delete_message)
                .setPositiveButton(R.string.alert_positive_label, (dialogInterface, i) -> delete(reminder))
                .setNegativeButton(R.string.alert_negative_label, ((dialog, i) -> dialog.cancel()))
                .create()
                .show();
    }

    private void delete(Reminder reminder) {
        listener.onDelete(reminder);
    }

    private void showPopup(View view, Reminder reminder) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.menu_fragment_reminder_action, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_fragment_reminder_action_edit:
                    showEditActivity(reminder);
                    return true;
                case R.id.menu_fragment_reminder_action_delete:
                    showAlert(reminder);
                    return true;
            }

            return false;
        });
        popup.show();
    }

    private void showEditActivity(Reminder reminder) {
        Intent intent = new Intent(context, EditReminderActivity.class)
                .putExtra(EditReminderActivity.REMINDER_EXTRA, reminder);
        context.startActivity(intent);
    }

    private String getReminderTime(String startingTime, int reminder) {
        String[] time = startingTime.split(":");

        String reminderTime = startingTime.substring(0, 5);
        if (reminder == 8) {
            reminderTime += ", ";
        } else {
            reminderTime += " & ";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(time[1]));
        calendar.add(Calendar.HOUR_OF_DAY, reminder);

        reminderTime += String.format(Locale.getDefault(), "%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        if (reminder == 8) {
            calendar.add(Calendar.HOUR_OF_DAY, reminder);
            reminderTime += String.format(Locale.getDefault(), " & %02d:%02d",
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        }

        return reminderTime;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public void setSelectedReminder(String selectedReminderName) {
        this.selectedReminderName = selectedReminderName;
        notifyDataSetChanged();
    }

    public interface OnDeleteMenuListener {
        void onDelete(Reminder reminder);
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_reminder_menu)
        ImageView ivMenu;

        @BindView(R.id.tv_item_reminder_medicine_name)
        TextView tvMedicineName;

        @BindView(R.id.tv_item_reminder_time)
        TextView tvReminderTime;

        OrdinaryViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    class SelectedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_reminder_active_menu)
        ImageView ivMenu;

        @BindView(R.id.tv_item_reminder_active_medicine_name)
        TextView tvMedicineName;

        @BindView(R.id.tv_item_reminder_active_time)
        TextView tvReminderTime;

        SelectedViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
