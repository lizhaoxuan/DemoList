package com.demo.zhaoxuanli.listdemo.settings;

import android.app.Application;
import android.provider.Settings;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by lizhaoxuan on 2017/9/5.
 */

public class DeveloperInfo {

    /**
     * developer info
     */
    public static final String IS_ALWAYS_FINISH = "is_always_finish";
    public static final String IS_ROOT = "is_root";
    public static final String DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled";
    public static final String ACCESSIBILITY_ENABLED = "accessibility_enabled";
    public static final String ENABLED_ACCESSIBILITY_SERVICES = "enabled_accessibility_services";
    public static final String MOCK_LOCATION = "mock_location";

    private DeveloperInfo() {
    }

    public static void putDeviceInfo(Map<String, Object> deviceMap, Application application) {
        deviceMap.put(IS_ALWAYS_FINISH, isAlwaysFinish(application));
        deviceMap.put(IS_ROOT, isRoot());

        deviceMap.put(DEVELOPMENT_SETTINGS_ENABLED, developmentSettingsEnabled(application));
        deviceMap.put(ACCESSIBILITY_ENABLED, accessibilityEnabled(application));
        deviceMap.put(ENABLED_ACCESSIBILITY_SERVICES, enabledAccessibilityServices(application));
        deviceMap.put(MOCK_LOCATION, mockLocation(application));
    }

    /**
     * 开发者模式是否启动
     */
    public static int developmentSettingsEnabled(Application application) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, -1);
        } else {
            return -1;
        }
    }

    /**
     * 是否开启了辅助模式
     */
    public static int accessibilityEnabled(Application application) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Secure.getInt(application.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, -1);
        } else {
            return -1;
        }
    }

    /**
     * 启用无障碍供应商名单
     */
    public static String enabledAccessibilityServices(Application application) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String enabledAccessibilityServices = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            String[] list = enabledAccessibilityServices.split(":");
            return new Gson().toJson(list);
        } else {
            return "";
        }
    }

    /**
     * 是否允许模拟位置
     */
    public static int mockLocation(Application application) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Secure.getInt(application.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, -1);
        } else {
            return -1;
        }
    }

    /**
     * 不保留活动是否开启
     */
    public static int isAlwaysFinish(Application application) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, -1);
        } else {
            return -1;
        }
    }

    /**
     * 是否root
     */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        return (new File(binPath)).exists() && isExecutable(binPath) || (new File(xBinPath)).exists() && isExecutable(xBinPath);
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        BufferedReader in = null;

        boolean var5;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String e = in.readLine();
            if (e == null || e.length() < 4) {
                return false;
            }
            char flag = e.charAt(3);
            if (flag != 115 && flag != 120) {
                return false;
            }
            var5 = true;
        } catch (IOException var16) {
            var16.printStackTrace();
            return false;
        } finally {
            if (p != null) {
                p.destroy();
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }
        }
        return var5;
    }
}
