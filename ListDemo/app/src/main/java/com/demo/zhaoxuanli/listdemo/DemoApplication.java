package com.demo.zhaoxuanli.listdemo;

import android.app.Application;

/**
 * Created by zhaoxuan.li on 2015/10/15.
 */
public class DemoApplication extends Application{

    private static DemoApplication instance;

    public static DemoApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
