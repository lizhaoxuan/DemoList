package com.demo.zhaoxuanli.listdemo.reflection;

/**
 * 分发组件 - 观察者
 * Created by lizhaoxuan on 16/3/7.
 */
public interface IObserver<T> {

    Class getType();

    void update(T data);



}
