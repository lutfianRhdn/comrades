package id.comrade.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.comrade.R;
import id.comrade.home.HomeFragment;
import id.comrade.profile.ProfileFragment;
import id.comrade.reminder.ReminderFragment;
import id.comrade.friendtoshare.FriendToShareFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    public static final int HOME_TAB = 0;
    public static final int FRIEND_TO_SHARE_TAB = 1;
    public static final int REMINDER_TAB = 2;
    public static final int PROFILE_TAB = 3;

    private Context context;

    MainPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        context = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case HOME_TAB:
                return HomeFragment.newInstance();
            case FRIEND_TO_SHARE_TAB:
                return FriendToShareFragment.newInstance();
            case REMINDER_TAB:
                return ReminderFragment.newInstance();
            case PROFILE_TAB:
                return ProfileFragment.newInstance();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case HOME_TAB:
                return context.getString(R.string.home_label);
            case FRIEND_TO_SHARE_TAB:
                return context.getString(R.string.friend_to_share_label);
            case REMINDER_TAB:
                return context.getString(R.string.label_reminder);
            case PROFILE_TAB:
                return context.getString(R.string.profile_label);
        }

        return "";
    }

    @Override
    public int getCount() {
        return 4;
    }
}
