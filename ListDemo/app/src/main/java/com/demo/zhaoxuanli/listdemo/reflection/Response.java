package com.demo.zhaoxuanli.listdemo.reflection;

/**
 * Created by lizhaoxuan on 16/3/23.
 */
public class Response<T> {

    public T data;

    public Response(T data) {
        this.data = data;
    }
}
