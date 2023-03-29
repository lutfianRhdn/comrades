package id.comrade.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.login.LoginActivity;
import id.comrade.samsungIntegration.ConsumerService;
import id.comrade.services.receiver.AlarmReceiver;
import id.comrade.utils.SharedPreferenceUtil;

public class MainActivity extends BaseActivity<MainViewState, MainViewModel> {
    private static final String TAG = MainActivity.class.getName();

    // For Samsung Wear
    private boolean mIsBound = false;
    private ConsumerService mConsumerService = null;

    @BindView(R.id.tl_activity_main)
    TabLayout tlMain;

    @BindView(R.id.toolbar_activity_main)
    Toolbar toolbarMain;

    @BindView(R.id.vp_activity_main)
    ViewPager viewPager;

    private MainPagerAdapter mainPagerAdapter;

    private TextView toolbarTitle;

    private String selectedReminderName;

    BluetoothHeadset mBluetoothHeadset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);

        toolbarTitle = toolbarMain.findViewById(R.id.toolbar_title);

        if (getSharedPreferences(SharedPreferenceUtil.PREF_HAS_LOGIN).equals("")) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        Log.i("FCM Token", task.getResult().getToken());
                        putSharedPreferences(
                                SharedPreferenceUtil.PREF_FCM_TOKEN, task.getResult().getToken());
                    });
            show(LoginActivity.class);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                toolbarTitle.setText(mainPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        toolbarTitle.setText(mainPagerAdapter.getPageTitle(0));

        tlMain.setupWithViewPager(viewPager);
        initTabs();

        selectedReminderName = getIntent().getStringExtra(AlarmReceiver.MEDICINE_EXTRA);
        if (selectedReminderName != null) {
            Log.i(TAG, "onCreate: SelectedReminder = " + selectedReminderName);
            show(MainPagerAdapter.REMINDER_TAB);
        }

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//        registerReceiver(mBluetoothStatusChangedReceiver,filter);
    }

    @Override
    public void onBackPressed() {
        if (tlMain.getSelectedTabPosition() == MainPagerAdapter.HOME_TAB) {
            super.onBackPressed();
        } else {
            show(MainPagerAdapter.HOME_TAB);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void show(int pos) {
        tlMain.getTabAt(pos).select();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPagerAdapter = null;
        viewPager.clearOnPageChangeListeners();

        // Clean up connections for samsung wear
//        if (mIsBound == true && mConsumerService != null) {
//            if (mConsumerService.closeConnection() == false) {
//            }
//        }
//        // Un-bind service for samsung wear
//        if (mIsBound) {
//            unbindService(mConnection);
//            mIsBound = false;
//        }
    }

    @Override
    protected void handleViewState(MainViewState viewState) {
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbarMain;
    }

    @Override
    protected boolean hasParent() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public String getSelectedReminderName() {
        return selectedReminderName;
    }

    @SuppressWarnings("ConstantConditions")
    private void initTabs() {
        int[] tabsIcon = {R.drawable.home, R.drawable.chat, R.drawable.bell, R.drawable.user};
        for (int i = 0; i < mainPagerAdapter.getCount(); i++) {
            TabLayout.Tab tabItem = tlMain.getTabAt(i);
            tabItem.setText("");
            tabItem.setIcon(tabsIcon[i]);
        }
    }

    //For samsung wear
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mConsumerService = ((ConsumerService.LocalBinder) service).getService();
            mConsumerService.findPeers();
            showToast("Connected to samsung wearable");
            mConsumerService.sendData("HOOOII");
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mConsumerService = null;
            mIsBound = false;
        }
    };

    // For Bluetooth Services
    private BroadcastReceiver mBluetoothStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d("CEK", "Bluetooth off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d("CEK", "Turning Bluetooth off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d("CEK", "Bluetooth turning on..");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d("CEK", "Bluetooth on");
                        mIsBound = bindService(new Intent(MainActivity.this, ConsumerService.class), mConnection, Context.BIND_AUTO_CREATE);
                        break;

                }
            }
        }
    };
}
