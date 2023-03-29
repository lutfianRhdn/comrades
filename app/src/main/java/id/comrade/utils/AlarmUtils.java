package id.comrade.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import id.comrade.model.Reminder;
import id.comrade.services.receiver.AlarmReceiver;

public class AlarmUtils {
    private static final int REMINDER_8 = 8 * 60 * 60 * 1000;
    private static final int REMINDER_12 = 12 * 60 * 60 * 1000;

    private AlarmUtils() {
    }

    @SuppressWarnings("ConstantConditions")
    public static void set(Context ctx, Reminder reminder) {
        int timeReminder = Integer.valueOf(reminder.getReminder());
        String[] time = reminder.getStartingTime().split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(time[1]));
        calendar.set(Calendar.SECOND, 0);

        long currentTimeMillis = new Date().getTime();

        if (currentTimeMillis > calendar.getTimeInMillis()) {
            calendar.add(Calendar.HOUR_OF_DAY, timeReminder);
        }

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ctx, AlarmReceiver.class)
                .putExtra(AlarmReceiver.MEDICINE_EXTRA, reminder.getNamaObat());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, (int) reminder.getId(),
                intent, 0);

        int repeatingTime = timeReminder == 8 ? REMINDER_8 : REMINDER_12;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                repeatingTime, pendingIntent);
    }

    @SuppressWarnings("ConstantConditions")
    public static void cancel(Context ctx, Reminder reminder) {
        Intent intent = new Intent(ctx, AlarmReceiver.class)
                .putExtra(AlarmReceiver.MEDICINE_EXTRA, reminder.getNamaObat());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, (int) reminder.getId(),
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
