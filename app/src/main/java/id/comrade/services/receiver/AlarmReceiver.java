package id.comrade.services.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import java.util.UUID;

import id.comrade.R;
import id.comrade.main.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String MEDICINE_EXTRA = "intent-extra:medicine";

    private static final String CHANNEL_ID = "CoraAlarm";

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onReceive(Context context, Intent intent) {
        long[] vibrationPattern = new long[] {0, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000,
                500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500};

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_ALARM)
                    .build();

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrationPattern);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
                    audioAttributes);
            notificationManager.createNotificationChannel(channel);
        }

        Bundle bundle = intent.getExtras();
        String medicine = bundle.getString(MEDICINE_EXTRA);
        Intent intentMainActivity = new Intent(context, MainActivity.class)
                .putExtra(MEDICINE_EXTRA, medicine);
        PendingIntent pIntent = PendingIntent.getActivity(context,
                UUID.randomUUID().hashCode(), intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.alarm_notification))
                .setContentText(medicine)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(medicine))
                .setContentIntent(pIntent)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVibrate(vibrationPattern)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.comrades_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.comrades_logo))
                .build();
        notification.audioStreamType = AudioManager.STREAM_ALARM;
        notificationManager.notify(56, notification);
    }
}
