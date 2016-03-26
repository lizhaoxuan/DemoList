package com.demo.zhaoxuanli.listdemo.reflection;

import android.util.Log;

/**
 * Created by lizhaoxuan on 16/3/23.
 */
public class Response<T> {

    public Class clazz;

    public T data;

    static {
        Log.d("TAG","AAA");
    }

    public Response(T data) {
        this.data = data;
        this.clazz = data.getClass();
    }
}
