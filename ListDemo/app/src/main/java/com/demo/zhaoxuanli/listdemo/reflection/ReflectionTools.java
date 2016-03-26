package com.demo.zhaoxuanli.listdemo.reflection;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by lizhaoxuan on 16/3/23.
 */
public class ReflectionTools {

    public static void getGeneric(Object object){


    }

    public static void addObServer(Object object){
        Class clazz = object.getClass();
        Log.d("TAG","类名："+clazz.getName());
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().contains("test")){
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {

                    Field[] fields = clas.getFields();
                    for (Field field : fields){
                        Log.d("TAG",field.getName() +"   "+field.getType());
                    }
                }
            }
        }

    }

}
