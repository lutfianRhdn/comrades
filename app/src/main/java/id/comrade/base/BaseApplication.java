package id.comrade.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

//import id.comrade.R;
//import id.comrade.main.MainActivity;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

//        RainbowSdk.instance().setNotificationBuilder(getApplicationContext(), MainActivity.class,
//                R.drawable.comrades_logo, getString(R.string.app_name), "Connect to the app",
//                R.color.colorPrimary);
//        RainbowSdk.instance().initialize();
    }
}
