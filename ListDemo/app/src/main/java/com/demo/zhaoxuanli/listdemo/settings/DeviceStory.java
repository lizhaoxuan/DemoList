package com.demo.zhaoxuanli.listdemo.settings;

import android.app.Application;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lizhaoxuan on 2017/9/12.
 */

public class DeviceStory {

    public static Map<String, Object> getAllDeviceInfo(Application application) {
        Map<String, Object> deviceInfoMap = new LinkedHashMap<>();
        DeviceInfo.putDeviceInfo(deviceInfoMap, application);
        DeveloperInfo.putDeviceInfo(deviceInfoMap, application);
        SettingsInfo.putDeviceInfo(deviceInfoMap, application);

        return deviceInfoMap;
    }

    public Map<String, String> getInfosForKey(Application application, String... keys) {
        if (keys == null) {
            return null;
        }
        Map<String, String> deviceInfoMap = new LinkedHashMap<>();
        for (String key : keys) {
            deviceInfoMap.put(key, getDeviceInfoForKey(application, key));
        }

        return deviceInfoMap;
    }

    public static String getDeviceInfoForKey(Application application, String key) {
        switch (key) {
            //DeviceInfo
            case DeviceInfo.APP_VERSION_NAME:
                return DeviceInfo.getAppVersionName(application);
            case DeviceInfo.APP_VERSION_CODE:
                return String.valueOf(DeviceInfo.getAppVersionCode(application));
            case DeviceInfo.CPU_INSTRUCTION_SET:
                return DeviceInfo.getCpuInstructionSet();
            case DeviceInfo.SDK_VERSION:
                return String.valueOf(DeviceInfo.getSdkVersion());
            case DeviceInfo.DEVICE_MAKER:
                return DeviceInfo.getDeviceMaker();
            case DeviceInfo.DEVICE_VERSION:
                return DeviceInfo.getDeviceVersion();
            case DeviceInfo.SD_SIZE:
                return DeviceInfo.getSDTotalSize(application) + "/" + DeviceInfo.getSDAvailableSize(application);
            case DeviceInfo.ROM_SIZE:
                return DeviceInfo.getRomTotalSize(application) + "/" + DeviceInfo.getRomAvailableSize(application);
            case DeviceInfo.IMEI:
                return DeviceInfo.getIMEI(application);
            case DeviceInfo.IMSI:
                return DeviceInfo.getIMSI(application);
            case DeviceInfo.PHONE_NUMBER:
                return DeviceInfo.getNativePhoneNumber(application);
            //DeveloperInfo
            case DeveloperInfo.IS_ALWAYS_FINISH:
                return String.valueOf(DeveloperInfo.isAlwaysFinish(application));
            case DeveloperInfo.IS_ROOT:
                return String.valueOf(DeveloperInfo.isRoot());
            case DeveloperInfo.DEVELOPMENT_SETTINGS_ENABLED:
                return String.valueOf(DeveloperInfo.developmentSettingsEnabled(application));
            case DeveloperInfo.ACCESSIBILITY_ENABLED:
                return String.valueOf(DeveloperInfo.accessibilityEnabled(application));
            case DeveloperInfo.ENABLED_ACCESSIBILITY_SERVICES:
                return DeveloperInfo.enabledAccessibilityServices(application);
            case DeveloperInfo.MOCK_LOCATION:
                return String.valueOf(DeveloperInfo.mockLocation(application));
            //SettingsInfo
            case SettingsInfo.LOCATION_MODE:
                return String.valueOf(SettingsInfo.locationMode(application));
            case SettingsInfo.LOCATION_MODE_DES:
                return String.valueOf(SettingsInfo.locationModeDes(SettingsInfo.locationMode(application)));
            case SettingsInfo.WIFI_ON:
                return String.valueOf(SettingsInfo.wifiOn(application));
            case SettingsInfo.WIFI_MAX_DHCP_RETRY_COUNT:
                return String.valueOf(SettingsInfo.wifiMaxDHCPRetryCount(application));
            case SettingsInfo.WIFI_SLEEP_POLICY:
                return String.valueOf(SettingsInfo.wifiSleepPolicy(application));
            case SettingsInfo.WIFI_SLEEP_POLICY_DES:
                return String.valueOf(SettingsInfo.wifiSleepPolicyDes(SettingsInfo.wifiSleepPolicy(application)));
            case SettingsInfo.BLUE_TOOTH_ON:
                return String.valueOf(SettingsInfo.blueToothOn(application));
            case SettingsInfo.SCREEN_OFF_TIMEOUT:
                return String.valueOf(SettingsInfo.screenOffTimeout(application));
            case SettingsInfo.SCREEN_BRIGHTNESS:
                return String.valueOf(SettingsInfo.screenBrightness(application));
            case SettingsInfo.SCREEN_BRIGHTNESS_MODE:
                return String.valueOf(SettingsInfo.screenBrightnessMode(application));
            case SettingsInfo.ACCELEROMETER_ROTATION:
                return String.valueOf(SettingsInfo.accelerometerRotation(application));
            case SettingsInfo.LOCAL_LANGUAGE:
                return String.valueOf(SettingsInfo.localLanguage(application));
            case SettingsInfo.IS_AUTO_TIME:
                return String.valueOf(SettingsInfo.isAutoTime(application));
            case SettingsInfo.IS_AUTO_TIME_ZONE:
                return String.valueOf(SettingsInfo.isAutoTimeZone(application));
            case SettingsInfo.TIME_ZONE:
                return String.valueOf(SettingsInfo.timeZone());
            case SettingsInfo.BATTERY_STATUS:
                return String.valueOf(SettingsInfo.batteryStatus(application));
            case SettingsInfo.BATTERY_STATUS_DES:
                return String.valueOf(SettingsInfo.batteryStatusDes(SettingsInfo.batteryStatus(application)));
            case SettingsInfo.AIRPLANE_MODE:
                return String.valueOf(SettingsInfo.airplaneMode(application));
            default:
                return "-";
        }
    }
}
