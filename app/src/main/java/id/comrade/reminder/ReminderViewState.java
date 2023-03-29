package id.comrade.reminder;

import java.util.List;

import id.comrade.base.BaseViewState;
import id.comrade.model.Reminder;

class ReminderViewState extends BaseViewState {
    private List<Reminder> reminders;

    private String message;

    ReminderViewState() {
        setLoading(true);
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
