package com.demo.zhaoxuanli.listdemo.thread_pool;

import android.os.Handler;
import android.os.Message;

import com.demo.zhaoxuanli.listdemo.default_widget.ProgressBarItem;

/**
 * describe
 * zhaoxuan.li
 * 2015/10/28.
 */
public class MyRunnable implements Comparable<MyRunnable>, Runnable {
    private int mBarItem;
    private String mName;
    private Handler mHandler;

    public MyRunnable(int barItem, String name,Handler handler){
        this.mBarItem = barItem;
        this.mName = name;
        this.mHandler = handler;
    }

    @Override
    public void run() {
        int i = 0;
        while(i<=100){
            try {
                Thread.sleep(50);
                i++;
                Message msg = mHandler.obtainMessage();
                msg.what = mBarItem;
                msg.arg1 = i;
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int compareTo(MyRunnable another) {
        return 0;
    }
}
