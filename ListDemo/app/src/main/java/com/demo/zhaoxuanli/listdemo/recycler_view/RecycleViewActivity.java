package com.demo.zhaoxuanli.listdemo.recycler_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.bluetooth.BlueToothActivity;
import com.demo.zhaoxuanli.listdemo.db_orm.SQLiteActivity;
import com.demo.zhaoxuanli.listdemo.draw_music.MusicActivity;
import com.demo.zhaoxuanli.listdemo.gyroscope.HeartActivity;
import com.demo.zhaoxuanli.listdemo.custom_widget.embedded.EmbedActivity;
import com.demo.zhaoxuanli.listdemo.music_player.MusicPlayerActivity;
import com.demo.zhaoxuanli.listdemo.custom_widget.popup_tips.NoDataTip;
import com.demo.zhaoxuanli.listdemo.thread_pool.ThreadPoolActivity;
import com.demo.zhaoxuanli.listdemo.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView myRecycleView;
    private List<ItemValue> myDatas;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private static Handler mHandler;

    private NoDataTip noDataTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        initData();
        initView();

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                //TopToast.makeText(RecycleViewActivity.this,"没有什么可刷新的，就是给你看一下").showPopupWindow(myRecycleView,TopToast.TitleHeight);
//                if (noDataTip.isShowing())
//                    noDataTip.hide();
//                else
//                    noDataTip.show(myRecycleView);
            }
        };

    }

    private void initView() {
        myAdapter = new MyAdapter(this, myDatas);
        myRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));
        myRecycleView.setAdapter(myAdapter);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//        //设置分隔线
//        myRecycleView.addItemDecoration(new DividerItemDecoration(
//                this, LinearLayoutManager.VERTICAL));

        myAdapter.setItemClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                startActivity(new Intent(RecycleViewActivity.this, myDatas.get(postion).getClass_t()));
            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //noDataTip = new NoDataTip(this);
        //noDataTip.show(myRecycleView);
    }

    private void initData() {

        myDatas = new ArrayList<>();
        myDatas.add(new ItemValue(0, "天气查询",
                "通过HTTP访问中央气象台，根据官方API进行解析，同时根据天气状况，切换背景图片",
                WeatherActivity.class));
        myDatas.add(new ItemValue(1, "音乐波形图",
                "读取系统音乐，并根据音量高低显示波形图", MusicActivity.class));
        myDatas.add(new ItemValue(2, "陀螺仪",
                "调用android陀螺仪传感器，晃动手机，改变图形形状", HeartActivity.class));
        myDatas.add(new ItemValue(3, "线程池Demo",
                "演示不同类型线程池效果", ThreadPoolActivity.class));
        myDatas.add(new ItemValue(5, "音乐播放器",
                "通过Service播放音乐，同时自定义通知栏消息，可对音乐进行控制", MusicPlayerActivity.class));
        myDatas.add(new ItemValue(6, "自定义orm数据库框架操作",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", SQLiteActivity.class));
        myDatas.add(new ItemValue(6, "蓝牙",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", BlueToothActivity.class));
        myDatas.add(new ItemValue(6, "动态控件封装",
                "封装NoDataTips,TopTips,Loading,ToolBar", EmbedActivity.class));


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


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
