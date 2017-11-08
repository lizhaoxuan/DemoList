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

import com.demo.zhaoxuanli.listdemo.DemoApplication;
import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.ScrollBanner.ScrollBannerActivity;
import com.demo.zhaoxuanli.listdemo.bluetooth.BlueToothActivity;
import com.demo.zhaoxuanli.listdemo.combo_widget.ComboActivity;
import com.demo.zhaoxuanli.listdemo.db_orm.SQLiteActivity;
import com.demo.zhaoxuanli.listdemo.distribute.DistributeActivity;
import com.demo.zhaoxuanli.listdemo.draw_music.MusicActivity;
import com.demo.zhaoxuanli.listdemo.focus_divert.FocusDivertActivity;
import com.demo.zhaoxuanli.listdemo.gyroscope.HeartActivity;
import com.demo.zhaoxuanli.listdemo.music_player.MusicPlayerActivity;
import com.demo.zhaoxuanli.listdemo.quietly_weak.QuietlyWeakActivity;
import com.demo.zhaoxuanli.listdemo.reflection.ReflectionActivity;
import com.demo.zhaoxuanli.listdemo.router.RouterActivity;
import com.demo.zhaoxuanli.listdemo.settings.GetSettingsActivity;
import com.demo.zhaoxuanli.listdemo.teach_case.TeachCaseActivity;
import com.demo.zhaoxuanli.listdemo.test.MergeRequestActivity;
import com.demo.zhaoxuanli.listdemo.thread_pool.ThreadPoolActivity;
import com.demo.zhaoxuanli.listdemo.view_move.ViewMoveActivity;
import com.demo.zhaoxuanli.listdemo.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView myRecycleView;
    private List<ItemValue> myDatas;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private static Handler mHandler;


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

            }
        };
    }

    private void initView() {
        myAdapter = new MyAdapter(this, myDatas);
        myRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
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
                startActivity(new Intent(DemoApplication.getInstance(), myDatas.get(postion).getClass_t()));
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
        myDatas.add(new ItemValue(0, "GetSettings",
                "获取手机的设备信息与设置信息", GetSettingsActivity.class));
        myDatas.add(new ItemValue(0, "Router",
                "通过Url启动activity", RouterActivity.class));
        myDatas.add(new ItemValue(0, "mergeRequst",
                "合并请求", MergeRequestActivity.class));
        myDatas.add(new ItemValue(0, "拖动按钮",
                "拖动按钮移动", ViewMoveActivity.class));
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
        myDatas.add(new ItemValue(7, "蓝牙",
                "打开蓝牙连接，进行搜索，即可选取蓝牙进行连接", BlueToothActivity.class));
        myDatas.add(new ItemValue(8, "动态控件封装",
                "封装NoDataTips,TopTips,Loading,ToolBar", ComboActivity.class));
        myDatas.add(new ItemValue(9, "ScrollBanner",
                "自动滚动banner", ScrollBannerActivity.class));
        myDatas.add(new ItemValue(10, "TeachCase",
                "新手提示控件", TeachCaseActivity.class));
        myDatas.add(new ItemValue(11, "偷偷唤醒",
                "偷偷唤醒手机", QuietlyWeakActivity.class));
        myDatas.add(new ItemValue(12, "java 反射机制测验",
                "java反射的一些测试应用", ReflectionActivity.class));
        myDatas.add(new ItemValue(13, "android 消息分发组件",
                "android 消息分发组件Demo", DistributeActivity.class));
        myDatas.add(new ItemValue(14, "焦点转移",
                "用于焦点转移测试", FocusDivertActivity.class));
        myDatas.add(new ItemValue(15, "拖动按钮",
                "拖动按钮移动", ViewMoveActivity.class));
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
