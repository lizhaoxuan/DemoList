package com.demo.zhaoxuanli.listdemo.draw_music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.demo.zhaoxuanli.listdemo.R;

public class MusicActivity extends AppCompatActivity {


    protected static final int RESULT_LOAD_IMAGE = 1;
    private MediaPlayer mPlayer;
    private VisualizerView mVisualizerView;
    private Button musicFileBut,musicStartBut,musicStopBut;

    private String musicPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        widgetInitialize();
        eventMonitor();

    }

    /*************************控件初始化*****************************/
    private void widgetInitialize(){
        musicFileBut = (Button)findViewById(R.id.musicFileBut);
        musicStartBut = (Button)findViewById(R.id.musicStartBut);
        musicStopBut = (Button)findViewById(R.id.musicStopBut);

        mPlayer = MediaPlayer.create(this, R.raw.test);
        mPlayer.setLooping(true);

        //mPlayer.start();

        // We need to link the visualizer view to the media player so that
        // it displays something
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        addLineRenderer();
    }
    /**************************事件监听**************************/
    private void eventMonitor(){
        musicFileBut.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                //打开音乐文件
                mPlayer.reset();
                mPlayer.stop();
                mPlayer.release();
                mVisualizerView.clearRenderers();

                Intent i=new Intent(Intent.ACTION_PICK , android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        musicStartBut.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                //开始
                if(mPlayer.isPlaying())
                    return ;
                if(musicPath==null){
                    mVisualizerView.clearRenderers();
                    mPlayer = MediaPlayer.create(MusicActivity.this, R.raw.test);
                }else{
                    try {
                        mPlayer.reset();
                        mPlayer.setDataSource(musicPath);
                    } catch (Exception e) {
                        System.out.println("读取音频异常");
                    }
                }
                try {
                    mPlayer.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();
                mVisualizerView.link(mPlayer);
                addLineRenderer();
            }
        });
        musicStopBut.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                //停止
                if(mPlayer==null){
                    return;
                }
                mPlayer.reset();
                mPlayer.stop();
                mPlayer.release();
                mPlayer = new MediaPlayer();
                mVisualizerView.clearRenderers();
            }
        });

    }


    private void addLineRenderer()
    {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerView.addRenderer(lineRenderer);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) //如果音乐选择返回OK
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            musicPath = cursor.getString(columnIndex);
            cursor.close();

            System.out.println("音乐链接是： "+musicPath);
            try {
                mPlayer = new MediaPlayer();
                mPlayer.reset();
                mPlayer.setDataSource(musicPath);
                mPlayer.prepare();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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
