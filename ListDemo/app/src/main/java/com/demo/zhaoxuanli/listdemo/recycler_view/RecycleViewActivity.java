package com.demo.zhaoxuanli.listdemo.recycler_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.bluetooth.BlueToothActivity;
import com.demo.zhaoxuanli.listdemo.gyroscope.HeartActivity;
import com.demo.zhaoxuanli.listdemo.draw_music.MusicActivity;
import com.demo.zhaoxuanli.listdemo.music_player.MusicPlayerActivity;
import com.demo.zhaoxuanli.listdemo.popup_tips.PopupTips;
import com.demo.zhaoxuanli.listdemo.popup_tips.TopToast;
import com.demo.zhaoxuanli.listdemo.thread_pool.ThreadPoolActivity;
import com.demo.zhaoxuanli.listdemo.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView myRecycleView;
    private List<ItemValue> myDatas;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        initData();
        initView();

        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                TopToast.makeText(RecycleViewActivity.this,"没有什么可刷新的，就是给你看一下").showPopupWindow(myRecycleView,TopToast.TitleHeight);


            }
        };

    }

    private void initView(){
        myAdapter = new MyAdapter(this, myDatas);
        myRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));
        myRecycleView.setAdapter(myAdapter);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
//        //设置分隔线
//        myRecycleView.addItemDecoration(new DividerItemDecoration(
//                this, LinearLayoutManager.VERTICAL));

        myAdapter.setItemClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                startActivity(new Intent(RecycleViewActivity.this,myDatas.get(postion).getClass_t()));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        });


    }

    private void initData(){

        myDatas = new ArrayList<>();
        myDatas.add(new ItemValue(0,"天气查询",
                "通过HTTP访问中央气象台，根据官方API进行解析，同时根据天气状况，切换背景图片",
                WeatherActivity.class));
        myDatas.add(new ItemValue(1,"音乐波形图",
                "读取系统音乐，并根据音量高低显示波形图", MusicActivity.class));
        myDatas.add(new ItemValue(2,"陀螺仪",
                "调用android陀螺仪传感器，晃动手机，改变图形形状", HeartActivity.class));
        myDatas.add(new ItemValue(3,"线程池Demo" ,
                "演示不同类型线程池效果", ThreadPoolActivity.class));
        myDatas.add(new ItemValue(5,"音乐播放器",
                "通过Service播放音乐，同时自定义通知栏消息，可对音乐进行控制", MusicPlayerActivity.class));
        myDatas.add(new ItemValue(6,"蓝牙",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", BlueToothActivity.class));
        myDatas.add(new ItemValue(6,"蓝牙",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", BlueToothActivity.class));
        myDatas.add(new ItemValue(6,"蓝牙",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", BlueToothActivity.class));
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycle_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            PopupTips tips = new PopupTips(this);
            tips.showPopupWindow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}