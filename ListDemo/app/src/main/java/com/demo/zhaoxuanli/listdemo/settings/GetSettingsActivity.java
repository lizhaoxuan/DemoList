package com.demo.zhaoxuanli.listdemo.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.tool.Underline2Camel;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetSettingsActivity extends AppCompatActivity {

    private TextView contentText;
    private Map<String, Object> deviceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_settings);
        contentText = (TextView) findViewById(R.id.contentText);

        deviceMap = new LinkedHashMap<>();
        DeviceInfo.putDeviceInfo(deviceMap, getApplication());
        DeveloperInfo.putDeviceInfo(deviceMap, getApplication());
        SettingsInfo.putDeviceInfo(deviceMap, getApplication());
        print(deviceMap);
        Log.d("TAG", "deviceMap.size():" + deviceMap.size());

    }


    private void print(Map<String, Object> deviceMap) {
        StringBuilder builder = new StringBuilder();
        Iterator iterator = deviceMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            builder.append(Underline2Camel.camel2Underline(String.valueOf(entry.getKey())))
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        contentText.setText(builder.toString());
        Log.d("TAG", "result:" + builder.toString());
    }

    private String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板：" + Build.BOARD);
        sb.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("\n系统定制商：" + Build.BRAND);
        sb.append("\ncpu指令集：" + Build.CPU_ABI);
        sb.append("\ncpu指令集2：" + Build.CPU_ABI2);
        sb.append("\n设置参数：" + Build.DEVICE);
        sb.append("\n显示屏参数：" + Build.DISPLAY);
        sb.append("\n无线电固件版本：" + Build.getRadioVersion());
        sb.append("\n硬件识别码：" + Build.FINGERPRINT);
        sb.append("\n硬件名称：" + Build.HARDWARE);
        sb.append("\nHOST:" + Build.HOST);
        sb.append("\n修订版本列表：" + Build.ID);
        sb.append("\n硬件制造商：" + Build.MANUFACTURER);
        sb.append("\n版本：" + Build.MODEL);
        sb.append("\n硬件序列号：" + Build.SERIAL);
        sb.append("\n手机制造商：" + Build.PRODUCT);
        sb.append("\n描述Build的标签：" + Build.TAGS);
        sb.append("\nTIME:" + Build.TIME);
        sb.append("\nbuilder类型：" + Build.TYPE);
        sb.append("\nUSER:" + Build.USER);
        return sb.toString();
    }
}
