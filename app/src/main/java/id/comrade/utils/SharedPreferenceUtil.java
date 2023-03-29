package id.comrade.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String PREF_HAS_LOGIN = "preference:hasLogin";
    public static final String PREF_FCM_TOKEN = "preference:fcmToken";

    private static SharedPreferenceUtil instance;

    private static final String SHARED_PREFERENCES_NAME = "id.comrades.preferences";

    private SharedPreferences sharedPreferences;

    private SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedPreferenceUtil(ctx);
        }

        return instance;
    }

    public void put(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public void clear() {
        sharedPreferences.edit()
                .clear()
                .apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key, "");
    }

}
