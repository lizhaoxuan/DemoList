package com.demo.zhaoxuanli.listdemo.quietly_weak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.DemoApplication;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lizhaoxuan on 16/1/15.
 */
public class Checkupdate extends BroadcastReceiver {
    private static final String TAG = "QuietlyWeak";

    private PowerManager.WakeLock mWakelock;

    public Checkupdate() {
        super();
        PowerManager pm = (PowerManager) DemoApplication.getInstance().getSystemService(
                DemoApplication.getInstance().POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.PARTIAL_WAKE_LOCK, "target");

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if ("org.long.action.setalarm".equals(intent.getAction())) {
            // TODO Auto-generated method stub
            Log.d(TAG, "收到广播");
            String msg = intent.getStringExtra("msg");
            //Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            new HttpThread().start();
        }
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
}
