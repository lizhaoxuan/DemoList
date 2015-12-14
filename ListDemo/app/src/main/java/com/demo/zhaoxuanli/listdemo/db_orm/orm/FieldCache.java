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

    private ArrayMap<Class, ArrayList<String>> fieldNameCache = new ArrayMap<>();

    private ArrayMap<Class, ArrayList<String>> typeCache = new ArrayMap<>();

    private ArrayMap<Class, String> classCache = new ArrayMap<>();


    /**
     * 取得Filed type缓存，如果不存在，则获取
     * Filed、FiledName 和FiledType是同时获取，某个不存在，则都不存在
     *
     * @param clazz class类
     */
    public ArrayList<Field> getFields(Class clazz) {
        ArrayList<Field> fields = fieldCache.get(clazz);
        if (fields == null) {
            fields = new ArrayList<>();
            putValue(fields, new ArrayList<String>(), new ArrayList<String>(), clazz);
        }

        return fields;
    }

    public ArrayList<String> getFieldNames(Class clazz) {
        ArrayList<String> fieldNames = fieldNameCache.get(clazz);
        if (fieldNames == null) {
            fieldNames = new ArrayList<>();
            putValue(new ArrayList<Field>(), fieldNames, new ArrayList<String>(), clazz);

        }
        return fieldNames;
    }

    public ArrayList<String> getFieldTypes(Class clazz) {
        ArrayList<String> types = typeCache.get(clazz);
        if (types == null) {
            types = new ArrayList<>();
            putValue(new ArrayList<Field>(), new ArrayList<String>(), types, clazz);
        }

        return types;
    }

    /**
     * 增加新的属性缓存
     *
     * @param fields 字段
     * @param names  字段名
     * @param types  字段类型
     * @param clazz  class 类
     */
    private void putValue(ArrayList<Field> fields, ArrayList<String> names, ArrayList<String> types, Class clazz) {
        ReflectionTool.createProperty(clazz, fields, names, types);
        fieldCache.put(clazz, fields);
        fieldNameCache.put(clazz, names);
        typeCache.put(clazz, types);
    }

    /**
     * 取得Class 下划线式命名，避免重复判断
     *
     * @param clazz class 类
     * @return 下划线格式类名
     */
    public String getClassName(Class clazz) {
        String className = classCache.get(clazz);
        if (className == null) {
            className = CamelCaseUtils.toUnderlineName(clazz.getSimpleName());
            classCache.put(clazz, className);
        }
        return className;
    }
}
