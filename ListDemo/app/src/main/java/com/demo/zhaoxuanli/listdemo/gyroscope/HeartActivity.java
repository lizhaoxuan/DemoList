package com.demo.zhaoxuanli.listdemo.gyroscope;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class HeartActivity extends AppCompatActivity {

    private MyView mAnimView = null;
    private Handler mainHandler;
    private TextView heartText;
    private int x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        heartText = (TextView)findViewById(R.id.heartText);
        mAnimView = new MyView(this);

        new Thread(new Update()).start();

        this.mainHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                heartText.setText("x: "+x+"  y: "+y +"  z: "+z);
                /*根据坐标添加动画*/
            }
        };
    }

    public class MyView implements SensorEventListener {
        /**SensorManager管理器**/
        private SensorManager mSensorMgr = null;
        private Sensor mSensor = null;
        /**重力感应X轴 Y轴 Z轴的重力值**/
        private float mGX = 0;
        private float mGY = 0;
        private float mGZ = 0;

        public MyView(Context context) {
            mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        public int getX(){
            return (int )mGX;
        }
        public int getY(){
            return (int )mGY;
        }
        public int getZ(){
            return (int )mGZ;
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO 自动生成的方法存根
            mGX = event.values[SensorManager.DATA_X];
            mGY= event.values[SensorManager.DATA_Y];
            mGZ = event.values[SensorManager.DATA_Z];

            //System.out.println("X: "+mGX+"  Y: "+mGY + "  Z: "+mGZ);
        }

    }

    public class Update implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(300);
                    x= mAnimView.getX();
                    y = mAnimView.getY();
                    z= mAnimView.getZ();
                    Message msg =HeartActivity.this.mainHandler.obtainMessage(1);
                    HeartActivity.this.mainHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public boolean onKeyDown(int KeyCode, KeyEvent event){
        //返回按钮监听
        if(KeyCode ==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            HeartActivity.this.finish();
        }
        return false;
    }




}
