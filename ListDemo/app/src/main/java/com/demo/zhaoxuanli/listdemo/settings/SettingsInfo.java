package com.demo.zhaoxuanli.listdemo.settings;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by lizhaoxuan on 2017/9/5.
 */

public class SettingsInfo {

    /**
     * settings info
     */
    public static final String LOCATION_MODE = "location_mode";
    public static final String LOCATION_MODE_DES = "location_mode_des";
    public static final String WIFI_ON = "wifi_on";
    public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";
    public static final String WIFI_SLEEP_POLICY = "wifi_sleep_policy";
    public static final String WIFI_SLEEP_POLICY_DES = "wifi_sleep_policy_des";
    public static final String BLUE_TOOTH_ON = "blue_tooth_on";
    public static final String SCREEN_OFF_TIMEOUT = "screen_off_timeout";
    public static final String SCREEN_BRIGHTNESS = "screen_brightness";
    public static final String SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode";
    public static final String ACCELEROMETER_ROTATION = "accelerometer_rotation";
    public static final String LOCAL_LANGUAGE = "local_language";
    public static final String IS_AUTO_TIME = "is_auto_time";
    public static final String IS_AUTO_TIME_ZONE = "is_auto_time_zone";
    public static final String TIME_ZONE = "time_zone";
    public static final String BATTERY_STATUS = "battery_status";
    public static final String BATTERY_STATUS_DES = "battery_status_des";
    public static final String AIRPLANE_MODE = "airplane_mode";

    private SettingsInfo() {
    }

    public static void putDeviceInfo(Map<String, Object> deviceMap, Application application) {
        int locationMode = locationMode(application);
        deviceMap.put(LOCATION_MODE, locationMode);
        deviceMap.put(LOCATION_MODE_DES, locationModeDes(locationMode));
        deviceMap.put(WIFI_ON, wifiOn(application));
        deviceMap.put(WIFI_MAX_DHCP_RETRY_COUNT, wifiMaxDHCPRetryCount(application));

        int wifiSleepPolicy = wifiSleepPolicy(application);
        deviceMap.put(WIFI_SLEEP_POLICY, wifiSleepPolicy);
        deviceMap.put(WIFI_SLEEP_POLICY_DES, wifiSleepPolicyDes(wifiSleepPolicy));

        deviceMap.put(BLUE_TOOTH_ON, blueToothOn(application));

        deviceMap.put(SCREEN_OFF_TIMEOUT, screenOffTimeout(application));
        deviceMap.put(SCREEN_BRIGHTNESS, screenBrightness(application));
        deviceMap.put(SCREEN_BRIGHTNESS_MODE, screenBrightnessMode(application));
        deviceMap.put(ACCELEROMETER_ROTATION, accelerometerRotation(application));

        deviceMap.put(LOCAL_LANGUAGE, localLanguage(application));

        deviceMap.put(IS_AUTO_TIME, isAutoTime(application));
        deviceMap.put(IS_AUTO_TIME_ZONE, isAutoTimeZone(application));
        deviceMap.put(TIME_ZONE, timeZone());

        int batteryStatus = batteryStatus(application);
        deviceMap.put(BATTERY_STATUS, batteryStatus);
        deviceMap.put(BATTERY_STATUS_DES, batteryStatusDes(batteryStatus));

        deviceMap.put(AIRPLANE_MODE, airplaneMode(application));
    }

    /**
     * 定位模式 -1：获取失败 0：位置服务关闭 1：仅限设备，使用GPS确定位置
     * 2：耗电量低：使用WLAN和移动网络确定位置
     * 3：准确度高：使用GPS WLAN 移动网络确定位置
     * other:其他
     */
    public static int locationMode(Application application) {
        int locationMode = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            locationMode = Settings.Secure.getInt(application.getContentResolver(), Settings.Secure.LOCATION_MODE, -1);
        }
        return locationMode;
    }

    /**
     * 定位模式描述
     */
    public static String locationModeDes(int locationMode) {
        switch (locationMode) {
            case -1:
                return "getFailure";
            case 0:
                return "off";
            case 1:
                return "sensorsOnly";
            case 2:
                return "batterySaving";
            case 3:
                return "highAccuracy";
            default:
                return "otherMode";
        }
    }

    /**
     * wifi打开状态
     */
    public static int wifiOn(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.WIFI_ON, -1);
        } else {
            return -1;
        }
    }

    /**
     * wifi 最大重连次数
     */
    public static int wifiMaxDHCPRetryCount(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.WIFI_MAX_DHCP_RETRY_COUNT, -1);
        } else {
            return -1;
        }
    }

    /**
     * 在休眠状态下保持WLAN网络连接策略
     * 0：默认策略，永不
     * 1：仅限充电时
     * 2：始终
     */
    public static int wifiSleepPolicy(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.WIFI_SLEEP_POLICY, -1);
        } else {
            return -1;
        }
    }

    /**
     * wifi休眠策略描述
     */
    public static String wifiSleepPolicyDes(int locationMode) {
        switch (locationMode) {
            case -1:
                return "getFailure";
            case 0:
                //默认策略
                return "sleepPolicyDefault";
            case 1:
                //仅限充电时
                return "neverWhilePlugged";
            case 2:
                //始终
                return "sleepPolicyNever";
            default:
                return "otherMode";
        }
    }

    /**
     * 蓝牙开关
     */
    public static int blueToothOn(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.BLUETOOTH_ON, -1);
        } else {
            return -1;
        }
    }

    /**
     * 屏幕休眠时间  毫秒
     */
    public static int screenOffTimeout(Application application) {
        return Settings.System.getInt(application.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, -1);
    }

    /**
     * 在0和255之间的屏幕背光亮度
     */
    public static int screenBrightness(Application application) {
        return Settings.System.getInt(application.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
    }

    /**
     * 控制是否启用自动亮度模式
     * 0：手动
     * 1：自动
     */
    public static int screenBrightnessMode(Application application) {
        return Settings.System.getInt(application.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, -1);
    }

    /**
     * 是否自动旋转屏幕
     */
    public static int accelerometerRotation(Application application) {
        return Settings.System.getInt(application.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, -1);
    }

    /**
     * 获取当前的语言模式
     */
    public static String localLanguage(Application application) {
        Locale locale = application.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    /**
     * 是否自动确定日期和时间
     */
    public static int isAutoTime(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.AUTO_TIME, -1);
        } else {
            return Settings.System.getInt(application.getContentResolver(), Settings.System.AUTO_TIME, -1);
        }
    }

    /**
     * 是否自动确定时区
     */
    public static int isAutoTimeZone(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(application.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, -1);
        } else {
            return Settings.System.getInt(application.getContentResolver(), Settings.System.AUTO_TIME_ZONE, -1);
        }
    }

    /**
     * 获取时区
     */
    public static String timeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT) + "::" + tz.getID();
    }

    /**
     * 获取当前充电状态
     */
    public static int batteryStatus(Application application) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = application.registerReceiver(null, filter);
        if (batteryStatusIntent != null) {
            return batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        } else {
            return -2;
        }
    }

    /**
     * 获取当前充电状态
     */
    public static String batteryStatusDes(int status) {
        switch (status) {
            case 0:
                return "charged";
            case BatteryManager.BATTERY_PLUGGED_AC:
                return "pluggedAC";
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "pluggedUsb";
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return "pluggedWireless";
            default:
                return "other";
        }
    }

    /**
     * 是否是飞行模式
     */
    public static int airplaneMode(Application application) {
        int airplaneMode = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            airplaneMode = Settings.Global.getInt(application.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
        }
        return airplaneMode;
    }

}
