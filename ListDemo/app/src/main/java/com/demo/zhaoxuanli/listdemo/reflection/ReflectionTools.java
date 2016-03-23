package com.demo.zhaoxuanli.listdemo.reflection;

import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * Created by lizhaoxuan on 16/3/23.
 */
public class ReflectionTools {

    public static void getGeneric(Object object){

        ParameterizedType parameterizedType = (ParameterizedType)object.getClass().getGenericSuperclass();

        Class clazz= (Class)(parameterizedType.getActualTypeArguments()[0]);
        Log.d("TAG",clazz.getName()+"  ");
    }

}
