package com.demo.zhaoxuanli.listdemo.router;

import android.content.Intent;

import java.io.UnsupportedEncodingException;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class Extra {

    private String key;

    private String typeKey;

    private Type type;

    private String value;

    public Extra(String key, String typeKey, String value) {
        this.key = key;
        this.typeKey = typeKey;
        this.type = Tool.convertorType(typeKey);
        this.value = value;
    }

    public void putExtra(Intent intent) throws RouterException, UnsupportedEncodingException {
        if (type != null) {
            type.putExtra(intent, Tool.decode(key), typeKey, Tool.decode(value));
        } else {
            throw new RouterException("Does not support parameter types :" + typeKey);
        }
    }


}
