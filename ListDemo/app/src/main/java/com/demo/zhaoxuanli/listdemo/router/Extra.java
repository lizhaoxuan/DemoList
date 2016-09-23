package com.demo.zhaoxuanli.listdemo.router;

import android.content.Intent;

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

    public void putExtra(Intent intent) throws RouterException {
        if (type != null) {
            type.putExtra(intent, key, value);
        } else {
            throw new RouterException("Does not support parameter types :" + typeKey);
        }
    }


}
