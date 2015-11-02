package com.demo.zhaoxuanli.listdemo.music_player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.draw_music.VisualizerView;

import java.io.IOException;

import static android.support.v7.app.NotificationCompat.*;

public class MusicPlayerActivity extends AppCompatActivity {
    private TextView musicUrlText;
    private Button musicFileBut,musicStartBut,musicStopBut;
    private String musicPath;
    public NotificationManager mNotificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initView();

    }


    private void initView(){
        musicUrlText = (TextView)findViewById(R.id.musicUrlText);
        musicStartBut = (Button)findViewById(R.id.musicStartBut);
        musicStopBut = (Button)findViewById(R.id.musicStopBut);
        musicFileBut = (Button)findViewById(R.id.musicFileBut);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        musicStartBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();  //Itent就是我们要发送的内容
                intent.setAction(MusicService.FLAG);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                if(musicStartBut.getText().toString().equals("播放")){
                    intent.putExtra("command", "start");
                    intent.putExtra("musicUrl", "start");
                    sendBroadcast(intent);   //发送广播
                    musicStartBut.setText("暂停");
                    showButtonNotify();
                }else{
                    intent.putExtra("command", "pause");
                    sendBroadcast(intent);   //发送广播
                    musicStartBut.setText("播放");
                }

            }
        });
        musicStopBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MusicPlayerActivity.this, MusicService.class));
                musicStartBut.setEnabled(false);
                mNotificationManager.cancelAll();// 删除你发的所有通知
            }
        });
        musicFileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK , android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });
    }

    /**
     * 歌曲ID：MediaStore.Audio.Media._ID
     Int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
     歌曲的名称：MediaStore.Audio.Media.TITLE
     String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
     歌曲的专辑名：MediaStore.Audio.Media.ALBUM
     String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

     歌曲的歌手名：MediaStore.Audio.Media.ARTIST
     String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

     歌曲文件的路径：MediaStore.Audio.Media.DATA
     String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
     歌曲的总播放时长：MediaStore.Audio.Media.DURATION
     Int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
     歌曲文件的大小：MediaStore.Audio.Media.SIZE
     Int size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
     * @param requestCode
     * @param resultCode
     * @param data
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        musicPath = cursor.getString(columnIndex);
        //String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

        cursor.close();

        System.out.println("音乐链接是： " + musicPath);

        musicUrlText.setText(musicPath);
        Intent in = new Intent(MusicPlayerActivity.this,MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putString("musicUrl", musicPath);
        in.putExtras(bundle);
        startService(in);
        musicStartBut.setEnabled(true);

    }

    /**
     * 带按钮的通知栏
     */
    public void showButtonNotify(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        mRemoteViews.setImageViewResource(R.id.nImageView, R.drawable.recycleview_ember);
        mRemoteViews.setViewVisibility(R.id.nStartBtn, View.VISIBLE);
        mRemoteViews.setViewVisibility(R.id.nStopBtn, View.VISIBLE);
        //
        //点击的事件处理
        Intent buttonIntent = new Intent(MusicService.FLAG);
        /*  播放   按钮  */
        buttonIntent.putExtra("command", "start");
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.nStartBtn, intent_prev);
		/* 暂停  按钮 */
        buttonIntent.putExtra("command", "pause");
        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.nStopBtn, intent_paly);

        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), Notification.FLAG_ONGOING_EVENT);

        mBuilder.setContent(mRemoteViews)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.drawable.recycleview_icon);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        //会报错，还在找解决思路
//		notify.contentView = mRemoteViews;
//		notify.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        mNotificationManager.notify(200, notify);
    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
