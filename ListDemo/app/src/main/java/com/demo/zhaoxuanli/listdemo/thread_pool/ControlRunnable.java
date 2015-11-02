package com.demo.zhaoxuanli.listdemo.thread_pool;

import android.os.Handler;
import android.os.Message;

/**
 * describe
 * zhaoxuan.li
 * 2015/10/29.
 */
public class ControlRunnable implements Runnable {

    private boolean cancelTask = false;  //是否取消任务

    private Handler mHandler;

    public ControlRunnable(int barItem, String name,Handler handler){
        this.mHandler = handler;
    }

    @Override
    public void run() {
        int i = 0;
        while(i<=100){
            try {
                if(!cancelTask) //没有取消
                {
                    Thread.sleep(50);
                    i++;
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = i;
                    mHandler.sendMessage(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setCancelTask(boolean can){
        this.cancelTask = can;
    }
}
