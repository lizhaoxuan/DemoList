package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * JAVA 反射工具
 * <p>
 * Created by lizhaoxuan on 15/11/5.
 */
public class ReflectionTool {

    /**
     * 筛选需要存到数据库的属性
     *
     * @param classT
     * @param types
     */
    public static void createProperty(Class classT, ArrayList<Field> fields,
                                      ArrayList<String> fieldNames , ArrayList<String> types) {

        Field[] _field = classT.getDeclaredFields();
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
                if (_fieldNames.contains(valueName)) {
                    int index = _fieldNames.indexOf(valueName);
                    // 保存属性
                    fields.add(_field[index]);
                    //保存属性名
                    String fieldName = _field[index].getName();
                    fieldNames.add(CamelCaseUtils.toUnderlineName(fieldName));
                    // 保存类型
                    types.add(_field[index].getGenericType().toString());
                }
            }
        }
    }

    /**
     * 依据类名 生成空值对象
     *
     * @param classT
     * @return
     */
    public static Object createObject(Class classT) {
        Object obj = null;

        try {
            Constructor ct = classT.getConstructor();
            obj = ct.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }


}
