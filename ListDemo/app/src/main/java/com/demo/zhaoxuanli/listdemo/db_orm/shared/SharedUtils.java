package com.demo.zhaoxuanli.listdemo.db_orm.shared;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.demo.zhaoxuanli.listdemo.DemoApplication;
import com.google.gson.Gson;

import java.util.List;


public class SharedUtils {
    private static SharedPreferences sharedPreferences;
    private static Editor editor;
    private static Gson gson;

    private SharedUtils() {
    }

    public static void init() {
        gson = new Gson();
        sharedPreferences = DemoApplication.getInstance().getSharedPreferences("demo_list", 0);
        editor = sharedPreferences.edit();
    }

    public static boolean set(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static boolean set(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String name) {
        return getString(name, "");
    }

    public static String getString(String name, String defaultValue) {
        return sharedPreferences.getString(name, defaultValue);
    }

    public static boolean set(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static boolean set(String key, long value) {
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void set(String key, Object obj) {
        try {
            String e = gson.toJson(obj);
            set(key, e);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static <T> T getObject(String name, Class<T> clazz) {
        T acc = null;

        try {
            String e = getString(name);
            acc = (gson.fromJson(e, clazz));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        try {
            return acc == null ? clazz.newInstance() : acc;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void set(String key, List<T> list) {
        try {
            String e = gson.toJson(list);
            set(key, e);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void remove(String key) {
        if (key != null) {
            editor.remove(key).commit();
        }
    }

    public static void clearAll() {
        editor.clear().commit();
    }
}
