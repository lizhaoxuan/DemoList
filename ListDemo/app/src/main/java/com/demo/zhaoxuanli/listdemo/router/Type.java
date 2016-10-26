package com.demo.zhaoxuanli.listdemo.router;

import android.content.Intent;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public enum Type {

    INT {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Integer.valueOf(value));
        }
    },
    LONG {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Long.valueOf(value));
        }
    },
    SHORT {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Short.valueOf(value));
        }
    },
    FLOAT {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Float.valueOf(value));
        }
    },
    DOUBLE {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Double.valueOf(value));
        }
    },
    BYTE {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Byte.valueOf(value));
        }
    },
    BOOLEAN {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Boolean.valueOf(value));
        }
    },
    CHAR {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            char[] characters = value.toCharArray();
            if (characters.length != 1) {
                throw new RouterException("can not cast to char:" + value);
            }
            intent.putExtra(key, characters[0]);
        }
    },
    JSON {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            Class clazz;
            try {
                clazz = Class.forName(type);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RouterException("can not cast to class:" + type);
            }
            intent.putExtra(key, (Serializable) Tool.fromJson(value, clazz));
        }
    },

    STRING {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, value);
        }
    },
    INT_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, int[].class));
        }
    },
    LONG_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, long[].class));
        }
    },
    SHORT_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, short[].class));
        }
    },
    FLOAT_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, float[].class));
        }
    },
    DOUBLE_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, double[].class));
        }
    },
    BYTE_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, byte[].class));
        }
    },
    BOOLEAN_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, boolean[].class));
        }
    },
    CHAR_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, value.toCharArray());
        }
    },
    STRING_ARRAY {
        @Override
        public void putExtra(Intent intent, String key, String type, String value) throws RouterException {
            intent.putExtra(key, Tool.fromJson(value, String[].class));
        }
    };

    public static final String INT_KEY = "i";
    public static final String LONG_KEY = "l";
    public static final String SHORT_KEY = "s";
    public static final String FLOAT_KEY = "f";
    public static final String DOUBLE_KEY = "d";
    public static final String BYTE_KEY = "bt";
    public static final String BOOLEAN_KEY = "bl";
    public static final String CHAR_KEY = "c";
    public static final String STRING_KEY = "str";
    public static final String INT_ARRAY_KEY = "i[]";
    public static final String LONG_ARRAY_KEY = "l[]";
    public static final String SHORT_ARRAY_KEY = "s[]";
    public static final String FLOAT_ARRAY_KEY = "f[]";
    public static final String DOUBLE_ARRAY_KEY = "d[]";
    public static final String BYTE_ARRAY_KEY = "bt[]";
    public static final String BOOLEAN_ARRAY_KEY = "bl[]";
    public static final String CHAR_ARRAY_KEY = "c[]";
    public static final String STRING_ARRAY_KEY = "str[]";

    public void print(String str) {
        Log.d("TAG", str);
    }

    public abstract void putExtra(Intent intent, String key, String type, String value) throws RouterException;
}
