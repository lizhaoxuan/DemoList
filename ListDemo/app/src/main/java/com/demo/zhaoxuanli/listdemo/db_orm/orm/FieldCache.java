package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.support.v4.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 类属性、类属性类型、类名缓存。
 * 避免多次获取
 * Created by lizhaoxuan on 15/12/10.
 */
public class FieldCache {

    private ArrayMap<Class, ArrayList<Field>> fieldCache = new ArrayMap<>();

    private ArrayMap<Class, ArrayList<String>> typeCache = new ArrayMap<>();

    private ArrayMap<Class, String> classCache = new ArrayMap<>();


    /**
     * 取得Filed缓存，如果不存在，则获取
     * Filed和type是同时获取，某个不存在，则都不存在
     *
     * @param clazz
     * @return
     */
    public ArrayList<Field> getFields(Class clazz) {
        ArrayList<Field> fields = fieldCache.get(clazz);
        if (fields == null) {
            fields = new ArrayList<>();
            ArrayList<String> types = new ArrayList<>();
            ReflectionTool.createProperty(clazz, fields, types);
            fieldCache.put(clazz, fields);
            typeCache.put(clazz, types);
        }

        return fields;
    }

    /**
     * 取得Filed type缓存，如果不存在，则获取
     * Filed和type是同时获取，某个不存在，则都不存在
     *
     * @param clazz
     * @return
     */
    public ArrayList<String> getFieldTypes(Class clazz) {
        ArrayList<String> types = typeCache.get(clazz);
        if (types == null) {
            types = new ArrayList<>();
            ArrayList<Field> fields = new ArrayList<>();
            ReflectionTool.createProperty(clazz, fields, types);
            fieldCache.put(clazz, fields);
            typeCache.put(clazz, types);
        }

        return types;
    }

    /**
     * 取得Class 下划线式命名，避免重复判断
     *
     * @param clazz
     * @return
     */
    public String getClassName(Class clazz) {
        String className = classCache.get(clazz);
        if (className == null) {
            className = CamelCaseUtils.toUnderlineName(clazz.getName());
            classCache.put(clazz, className);
        }
        return className;
    }
}
