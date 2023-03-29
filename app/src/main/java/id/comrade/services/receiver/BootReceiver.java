package id.comrade.services.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.annimon.stream.Stream;

import id.comrade.repositories.ReminderCache;
import id.comrade.utils.AlarmUtils;
import io.reactivex.disposables.CompositeDisposable;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ReminderCache reminderCache = ReminderCache.getInstance((Application) context.getApplicationContext());

            CompositeDisposable disposables = new CompositeDisposable();

            disposables.add(reminderCache.getCache()
                    .subscribe(reminders -> {
                        Stream.of(reminders).forEach(reminder -> AlarmUtils.set(context, reminder));
                        disposables.dispose();
                    }));

        }
    }
}
