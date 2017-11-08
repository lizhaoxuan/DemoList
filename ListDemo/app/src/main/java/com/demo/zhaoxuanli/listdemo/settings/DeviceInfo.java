package com.demo.zhaoxuanli.listdemo.settings;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

/**
 * Created by lizhaoxuan on 2017/9/5.
 */

public class DeviceInfo {

    /**
     * device info
     */
    public static final String APP_VERSION_NAME = "app_version_name";
    public static final String APP_VERSION_CODE = "app_version_code";
    public static final String CPU_INSTRUCTION_SET = "cpu_instruction_set";
    public static final String SDK_VERSION = "sdk_version";
    public static final String DEVICE_MAKER = "device_maker";
    public static final String DEVICE_VERSION = "device_version";
    public static final String SD_SIZE = "sd_size";
    public static final String ROM_SIZE = "rom_size";
    public static final String IMEI = "imei";
    public static final String IMSI = "imsi";
    public static final String PHONE_NUMBER = "phoneNumber";


    private DeviceInfo() {
    }

    public static void putDeviceInfo(Map<String, Object> deviceMap, Application application) {
        PackageInfo packageInfo = getPackageInfo(application);
        if (packageInfo != null) {
            deviceMap.put(APP_VERSION_NAME, packageInfo.versionName);
            deviceMap.put(APP_VERSION_CODE, packageInfo.versionCode);
        }

        deviceMap.put(CPU_INSTRUCTION_SET, getCpuInstructionSet());
        deviceMap.put(SDK_VERSION, getSdkVersion());
        deviceMap.put(DEVICE_MAKER, getDeviceMaker());
        deviceMap.put(DEVICE_VERSION, getDeviceVersion());
        deviceMap.put(SD_SIZE, getSDTotalSize(application) + "/" + getSDAvailableSize(application));
        deviceMap.put(ROM_SIZE, getRomTotalSize(application) + "/" + getRomAvailableSize(application));
        deviceMap.put(IMEI, getIMEI(application));
        deviceMap.put(IMSI, getIMSI(application));
        deviceMap.put(PHONE_NUMBER, getNativePhoneNumber(application));
    }

    /**
     * app版本
     */
    public static String getAppVersionName(Application application) {
        return getPackageInfo(application).versionName;
    }

    public static int getAppVersionCode(Application application) {
        return getPackageInfo(application).versionCode;
    }

    private static PackageInfo getPackageInfo(Application application) {
        PackageInfo packageInfo = null;
        try {
            PackageManager pm = application.getPackageManager();
            packageInfo = pm.getPackageInfo(application.getPackageName(), 0);
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return packageInfo;
    }

    /**
     * CPU架构
     */
    public static String getCpuInstructionSet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Gson().toJson(Build.SUPPORTED_ABIS);
        } else {
            return Build.CPU_ABI;
        }
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDeviceMaker() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceVersion() {
        return Build.MODEL;
    }

    /**
     * 获得SD卡总大小
     */
    public static String getSDTotalSize(Application application) {
        File path = Environment.getExternalStorageDirectory();
        if (path == null || "".equals(path.getPath())) {
            return "--";
        }
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(application, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    public static String getSDAvailableSize(Application application) {
        File path = Environment.getExternalStorageDirectory();
        if (path == null || "".equals(path.getPath())) {
            return "--";
        }
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(application, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     */
    public static String getRomTotalSize(Application application) {
        File path = Environment.getDataDirectory();
        if (path == null || "".equals(path.getPath())) {
            return "--";
        }
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(application, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     */
    public static String getRomAvailableSize(Application application) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(application, blockSize * availableBlocks);
    }

    /**
     * 获取IMIE号码
     */
    public static String getIMEI(Application application) {
        TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Application application) {
        TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Application application) {
        TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }
}
