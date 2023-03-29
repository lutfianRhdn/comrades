package id.comrade.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.comrade.R;
import id.comrade.model.Reminder;

public class DateUtils {

    private static final SimpleDateFormat DATE_SDF = new SimpleDateFormat("dd MMMM yyyy",
            Locale.getDefault());

    private static final SimpleDateFormat TIME_SDF = new SimpleDateFormat("HH:mm",
            Locale.getDefault());

    private static final SimpleDateFormat DATE_DB_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private static final int MORNING_TIME = 5;
    private static final int NOON_TIME = 10;
    private static final int EVENING_TIME = 15;
    private static final int NIGHT_TIME = 18;

    private static final int MILLISECOND_PER_SECOND = 1000;
    private static final int SECOND_PER_MINUTE = 60;
    private static final int MINUTE_PER_HOUR = 60;
    private static final int HOUR_PER_DAY = 24;
    private static final int MAX_PAST_DAY = 3;

    private DateUtils() {
    }

    public static String getDate() {
        return DATE_SDF.format(Calendar.getInstance().getTime());
    }

    public static String getTime() {
        return TIME_SDF.format(Calendar.getInstance().getTime());
    }

    public static String format(String dateString) {
        Date date;
        try {
            date = DATE_DB_FORMAT.parse(dateString);
        } catch (ParseException ex) {
            return "";
        }

        return DATE_SDF.format(date);
    }

    public static String getDifference(Context ctx, String tanggal) {
        Date today = new Date();
        Date postDate;
        try {
            postDate = DATE_DB_FORMAT.parse(tanggal);
        } catch (ParseException ex) {
            return "";
        }

        long timeDiff = today.getTime() - postDate.getTime();

        String time;
        long minuteDiff = timeDiff / MILLISECOND_PER_SECOND / SECOND_PER_MINUTE;
        if (minuteDiff < MINUTE_PER_HOUR) {
            time = ctx.getString(R.string.time_difference_minute, minuteDiff);
        } else {
            long hourDiff = minuteDiff / MINUTE_PER_HOUR;
            if (hourDiff < HOUR_PER_DAY) {
                time = ctx.getString(R.string.time_difference_hour, hourDiff);
            } else {
                long dayDiff = hourDiff / HOUR_PER_DAY;
                if (dayDiff < MAX_PAST_DAY) {
                    time = ctx.getString(R.string.time_difference_day, dayDiff);
                } else {
                    time = DATE_SDF.format(postDate);
                }
            }
        }

        return time;
    }

    public static String getTimeDescription(Context context) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= NIGHT_TIME || hour <= MORNING_TIME) {
            return context.getString(R.string.night_label);
        } else if (hour >= EVENING_TIME) {
            return context.getString(R.string.evening_label);
        } else if (hour >= NOON_TIME) {
            return context.getString(R.string.noon_label);
        } else {
            return context.getString(R.string.morning_label);
        }
    }

    public static Reminder findClosest(List<Reminder> reminders) {
        if (reminders == null || reminders.size() == 0) {
            return null;
        }

        Date date = new Date();

        Reminder minReminder = reminders.get(0);
        long minTimeInMillis = getClosestMillisecondReminder(minReminder.getStartingTime(),
                Integer.valueOf(minReminder.getReminder()), date.getTime());

        if (reminders.size() != 0) {
            for (int i = 1; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                long timeInMillis = getClosestMillisecondReminder(reminder.getStartingTime(),
                        Integer.valueOf(reminder.getReminder()), date.getTime());

                if (timeInMillis < minTimeInMillis && timeInMillis > 0) {
                    minTimeInMillis = timeInMillis;
                    minReminder = reminder;
                }
            }
        }

        return minReminder;
    }

    public static String getReminderRemainingTime(Context context, String startingTime, int reminder) {
        Date date = new Date();
        long currentTime = date.getTime();
        long closestReminderTime = getClosestMillisecondReminder(startingTime, reminder, currentTime);

        long totalSeconds = closestReminderTime / MILLISECOND_PER_SECOND;
        long totalMinutes = totalSeconds / SECOND_PER_MINUTE;
        if (totalMinutes < MINUTE_PER_HOUR) {
            return context.getString(R.string.time_upcoming_minutes, totalMinutes);
        } else {
            long totalHours = totalMinutes / MINUTE_PER_HOUR;
            return context.getString(R.string.time_upcoming_hours, totalHours);
        }
    }

    private static long getClosestMillisecondReminder(String startingTime, int reminder,
                                                      long currentMillis) {
        Calendar calendar = getCalendarFromStartingTime(startingTime);

        long startingMillis = calendar.getTimeInMillis() - currentMillis;
        calendar.add(Calendar.HOUR_OF_DAY, reminder);
        long secondMillis = calendar.getTimeInMillis() - currentMillis;
        long minMillis = startingMillis;

        if (startingMillis < 0) {
            minMillis = secondMillis;
        }

        if (reminder == 8 && minMillis < 0) {
            calendar.add(Calendar.HOUR_OF_DAY, reminder);
            minMillis = calendar.getTimeInMillis() - currentMillis;
        }

        return minMillis;
    }

    private static Calendar getCalendarFromStartingTime(String startingTime) {
        String[] times = startingTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        return calendar;
    }
}
