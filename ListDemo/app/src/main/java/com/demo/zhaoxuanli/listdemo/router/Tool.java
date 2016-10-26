package com.demo.zhaoxuanli.listdemo.router;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class Tool {
    private static final Gson GSON = new Gson();

    private Tool() {
    }

    public static Type convertorType(String type) {
        switch (type) {
            case Type.INT_KEY:
                return Type.INT;
            case Type.LONG_KEY:
                return Type.LONG;
            case Type.SHORT_KEY:
                return Type.SHORT;
            case Type.FLOAT_KEY:
                return Type.FLOAT;
            case Type.DOUBLE_KEY:
                return Type.DOUBLE;
            case Type.BYTE_KEY:
                return Type.BYTE;
            case Type.BOOLEAN_KEY:
                return Type.BOOLEAN;
            case Type.CHAR_KEY:
                return Type.CHAR;
            case Type.INT_ARRAY_KEY:
                return Type.INT_ARRAY;
            case Type.LONG_ARRAY_KEY:
                return Type.LONG_ARRAY;
            case Type.SHORT_ARRAY_KEY:
                return Type.SHORT_ARRAY;
            case Type.FLOAT_ARRAY_KEY:
                return Type.FLOAT_ARRAY;
            case Type.DOUBLE_ARRAY_KEY:
                return Type.DOUBLE_ARRAY;
            case Type.BYTE_ARRAY_KEY:
                return Type.BYTE_ARRAY;
            case Type.BOOLEAN_ARRAY_KEY:
                return Type.BOOLEAN_ARRAY;
            case Type.CHAR_ARRAY_KEY:
                return Type.CHAR_ARRAY;
            case Type.STRING_ARRAY_KEY:
                return Type.STRING_ARRAY;
            default:
                return Type.JSON;
        }
    }

    public static String decode(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str, "UTF-8");
    }

    public static String encode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }}
