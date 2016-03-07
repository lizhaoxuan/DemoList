package com.demo.zhaoxuanli.listdemo.distribute;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务分发池
 * Created by lizhaoxuan on 16/3/7.
 */
public class DistributePool {

    private static DistributePool instance;

    //private List<DisObserver> observers;
    private List<Object> observers;

    public static DistributePool getInstance() {
        if (instance == null) {
            instance = new DistributePool();
        }
        return instance;
    }

    public DistributePool() {
        observers = new LinkedList<>();
    }

    public void addObserver(DisObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }


    public void addObserver(Object observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void addData(Object data) {
        for (int i = 0, length = observers.size(); i < length; i++) {
            Object observer = observers.get(i);
            //移除无效的观察者
            if (observer == null) {
                observers.remove(i);
                continue;
            }
            //符合条件
            Method m1 = foundObserver(data, observer);
            if (m1 != null) {
                Log.e("TAG", "找到了");
                try {
                    m1.invoke(observer,data);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("TAG", "没找到");
            }
        }
    }

//    public void addData(Object data) {
//        for (int i = 0, length = observers.size(); i < length; i++) {
//            DisObserver observer = observers.get(i);
//            //移除无效的观察者
//            if (observer == null) {
//                observers.remove(i);
//                continue;
//            }
//            //符合条件
//            if (foundObserver(data,observer)){
//                observer.distributeEvent(data);
//            }
//        }
//    }

        /**
         * 判断是否是目标观察者
         *
         * @param target   目标类型
         * @param observer 观察者
         * @return
         */

    private Method foundObserver(Object target, Object observer) {
        Class clazz = observer.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Log.e("TAG", method.getName());
            if (method.getName().contains("onEvent")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    if (target.getClass().getName().equals(clas.getName()))
                        return method;
                }
            }
        }
        return null;

    }

    /**
     * 判断是否是目标观察者
     *
     * @param target   目标类型
     * @param observer 观察者
     * @return
     */
    private boolean foundObserver(Object target, DisObserver observer) {
        Class clazz = observer.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("distributeEvent".equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    if (target.getClass().getName().equals(clas.getName()))
                        return true;
                }
            }
        }
        return false;

    }

}
