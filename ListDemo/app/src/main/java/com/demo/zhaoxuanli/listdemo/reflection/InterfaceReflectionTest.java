package com.demo.zhaoxuanli.listdemo.reflection;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by lizhaoxuan on 16/3/7.
 */
public class InterfaceReflectionTest {


    public static void addObserver(IObserver observer){

        Class clazz = observer.getClass();
        //Log.e("TAG",clazz.getName());

        Method [] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("update".equals(method.getName())){
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    if (UserDto.class.getName().equals(clas.getName()))
                        observer.update(null);
                }
            }
//            String methodName = method.getName();
//            Log.e("TAG", "方法名称:" + methodName);

            //Log.e("TAG", "*****************************");
        }
    }

}
