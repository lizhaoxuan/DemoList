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
            case Type.STRING_KEY:
                return Type.STRING;
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

}
