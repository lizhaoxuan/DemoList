package com.demo.zhaoxuanli.listdemo.router;

import android.content.Intent;
import android.util.Log;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public enum Type {

    INT {
        @Override
        public void print(String str) {
            super.print("int:" + str);
        }

        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Integer.valueOf(value));
        }
    },
    LONG {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Long.valueOf(value));
        }
    },
    SHORT {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Short.valueOf(value));
        }
    },
    FLOAT {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Float.valueOf(value));
        }
    },
    DOUBLE {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Double.valueOf(value));
        }
    },
    BYTE {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Byte.valueOf(value));
        }
    },
    BOOLEAN {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, Boolean.valueOf(value));
        }
    },
    CHAR {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            char[] characters = value.toCharArray();
            if (characters.length != 1) {
                throw new RouterException("can not cast to char:" + value);
            }
            intent.putExtra(key, characters[0]);
        }
    },
    STRING {
        @Override
        public void putExtra(Intent intent, String key, String value) throws RouterException {
            intent.putExtra(key, value);
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


    public void print(String str) {
        Log.d("TAG", str);
    }

    public abstract void putExtra(Intent intent, String key, String value) throws RouterException;
}
