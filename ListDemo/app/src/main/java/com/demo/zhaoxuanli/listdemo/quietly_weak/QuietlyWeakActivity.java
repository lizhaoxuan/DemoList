package com.demo.zhaoxuanli.listdemo.quietly_weak;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.demo.zhaoxuanli.listdemo.DemoApplication;
import com.demo.zhaoxuanli.listdemo.R;

import java.net.HttpURLConnection;
import java.net.URL;


public class QuietlyWeakActivity extends AppCompatActivity {
    private static final String TAG = "QuietlyWeak";

    private Button btn1;
    private PowerManager.WakeLock mWakelock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quietly_weak);
        btn1 = (Button)findViewById(R.id.btn1);

        PowerManager pm = (PowerManager) DemoApplication.getInstance().getSystemService(
                DemoApplication.getInstance().POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "target");


        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent("ELITOR_CLOCK");
        intent.putExtra("msg", "你该打酱油了");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, pi);
        Log.d(TAG, "发广播");

        registerReceiver();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopAutoBrightness(QuietlyWeakActivity.this);
                        setBrightness(QuietlyWeakActivity.this,0);
                        lightScreen();
                    }
                },5000);
            }
        });
    }

    private void registerReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction("ELITOR_CLOCK");

        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                //Log.d(TAG, "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "screen on");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "screen off");
                    //restoreScreen();
                } else if ("ELITOR_CLOCK".equals(action)) {
                    Log.d(TAG, "alarm 广播");
                    new HttpThread().start();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);

    }

    class HttpThread extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("http://www.baidu.com");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(TAG, "相应码：" + conn.getResponseCode());
            } catch (Exception e) {
                Log.d(TAG, "访问失败");
                lightScreen();
                e.printStackTrace();

            }
        }
    }

    /**
     * 点亮屏幕
     */
    private void lightScreen() {
        mWakelock.acquire();
        mWakelock.release();
    }


    /**
     * 是否开启自动亮度调节
     *
     * @param aContentResolver
     * @return
     */
    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return automicBrightness;
    }

    /**
     * 获取屏幕亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     */
    public static void setBrightness(Activity activity, int brightness) {
        // Settings.System.putInt(activity.getContentResolver(),
        // Settings.System.SCREEN_BRIGHTNESS_MODE,
        // Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        Log.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness);
        activity.getWindow().setAttributes(lp);
    }

    //那么，能设置了，但是为什么还是会出现，设置了，没反映呢？
    //嘿嘿，那是因为，开启了自动调节功能了，那如何关闭呢？这才是最重要的：

    /**
     * 停止自动亮度调节
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }
    //能开启，那自然应该能关闭了哟哟，那怎么关闭呢？很简单的：

    /**
     * 开启亮度自动调节 *
     *
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    //至此，应该说操作亮度的差不多都有了，结束！
    //哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！

    /**
     * 保存亮度设置状态
     */
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness", brightness);
        // resolver.registerContentObserver(uri, true, myContentObserver);
        resolver.notifyChange(uri, null);
    }

}
