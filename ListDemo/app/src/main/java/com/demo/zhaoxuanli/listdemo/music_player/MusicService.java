package com.demo.zhaoxuanli.listdemo.music_player;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.draw_music.MusicActivity;

import java.io.IOException;

public class MusicService extends Service {
    public static final String FLAG = "MUSIC";
    private MediaPlayer mPlayer = new MediaPlayer();
    private ReceiveBroadCast receiveBroadCast;  //广播实例
    private String mMusicPath = "";


    public MusicService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("重新启动！！！！");
        // 注册广播接收



        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FLAG);   //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);


        return super.onStartCommand(intent, flags, startId);
    }


    private void initPlayer(String musicUrl){
        try {
            mPlayer.reset();
            mPlayer.setDataSource(musicUrl);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ReceiveBroadCast extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            //得到广播中得到的数据，并显示出来
            String command = intent.getStringExtra("command");
            switch (command){
                case "musicPath":
                    String path = intent.getStringExtra("musicPath");
                    initPlayer(path);
                    break;
                case "start":
                    start();
                    break;
                case "stop":
                    stop();
                    break;
                case "pause": //暂停
                    pause();
                    break;
            }
        }
    }

    private void start(){
        if(mPlayer == null){
            if(mMusicPath==null||mMusicPath.equals("")){
                mPlayer = MediaPlayer.create(MusicService.this, R.raw.test);
            }else{
                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(mMusicPath);
                    mPlayer.prepare();
                } catch (Exception e) {
                    mPlayer = MediaPlayer.create(MusicService.this, R.raw.test);
                    System.out.println("读取音频异常");
                }
            }
            mPlayer.start();
        }else if(mPlayer.isPlaying()){
            return ;
        }else{
            mPlayer.start();
        }
    }

    private void stop(){
        if(mPlayer!=null)
            mPlayer.stop();
    }

    private void pause(){
        if(mPlayer != null && mPlayer.isPlaying())
        {
            mPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        mPlayer.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
