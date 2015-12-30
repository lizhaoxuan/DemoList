package com.demo.zhaoxuanli.listdemo.teach_case;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.R;

public class TeachCaseActivity extends AppCompatActivity {
    private static final String TAG = TeachCaseActivity.class.getName();
    private static final int ORDER = 1;

    private MyHandler mHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_case);

        mHandler = new MyHandler();
        new MyThread(mHandler).start();

    }


    private void doSomeThing(){

        //Log.d(TAG,"收到message");

    }


    private class MyThread extends Thread{

        private Handler handler;

        public MyThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage(TeachCaseActivity.ORDER));
        }
    }


    private class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ORDER :
                    doSomeThing();
                    break;
            }
        }
    }

}
