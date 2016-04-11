package com.demo.zhaoxuanli.listdemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.zhaoxuanli.listdemo.recycler_view.RecycleViewActivity;
import com.demo.zhaoxuanli.listdemo.tool.ViewServer;


/**
 * ComboActivity Loading动态图效果
 * 外层：recycleView（可展开显示详情）+可变化title+后期添加下拉刷新
 * <p>
 * ----------------以OK --------------
 * Demo:天气api查询+简单http请求  OK
 * Demo:音乐波形图   OK
 * Demo: 陀螺仪传感  OK
 * Demo:线程池的Demo OK    （界面优化）
 * Demo:service - 音乐播放器  OK  （界面优化-提示栏优化）
 * Demo:蓝牙  ok
 * <p>
 * ----------------下一个------------
 * Demo:图片压缩
 * Demo:xml读取Demo配置文件
 * <p>
 * <p>
 * <p>
 * <p>
 * ----------------计划中---------------
 * Demo:画板 - 可进行简单绘画的画板
 * Demo:图片截取
 * <p>
 * Demo:横版View，仿表情栏消息
 * Demo:各种自定义形状view，自定义控件（扇形菜单，下拉刷新）
 * Demo：ListView异步加载图片，滑动中停止加载
 * Demo:通知栏消息监记录并生成桌面插件
 * Demo:可隐藏式Title，并根据当前Item改变颜色
 * Demo:卡片加漂浮式第三方控件
 * <p>
 * <p>
 * ------------------小玩意-----------------
 * 点赞详情
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewServer.get(this).addWindow(this);
        setContentView(R.layout.activity_main);

        Intent in = new Intent(this, RecycleViewActivity.class);
        startActivity(in);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
