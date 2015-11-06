package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * JAVA 反射工具
 *
 * Created by lizhaoxuan on 15/11/5.
 */
public class ReflectionTool {


    /**
     * 筛选需要存到数据库的属性
     * @param classT
     * @param types
     */
    public static void createProperty(Class classT,ArrayList<Field> fieldList ,ArrayList<String> types){

        Field[] _field =classT.getDeclaredFields();
        ArrayList<String> _fieldNames = new ArrayList<>();
        Method methods[] = classT.getMethods();

        for (Field field : _field) {

            _fieldNames.add(field.getName());
        }

        //遍历所有方法
        for (Method method : methods) {
            String name = method.getName();
            //判断是否含有setter属性
            if (name.contains("set") && !name.equals("offset")) {
                String valueName = name.substring(3).substring(0, 1).toLowerCase() + name.substring(4);
                //如果所有的setter方法中含有属性名，那么就是要存储的
                if(_fieldNames.contains(valueName)){
                    int index = _fieldNames.indexOf(valueName);
                    fieldList.add(_field[index]);  // 找到确定要保存的属性，存入List中
                    types.add(_field[index].getGenericType().toString()); // 类型
                }
            }
        }
    }

    /**
     * 依据类名 生成空值对象
     * @param classT
     * @return
     */
    public static Object createObject(Class classT){
        Object obj = null;

        try {
            Constructor ct = classT.getConstructor(null);
            obj = ct.newInstance(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }




}
