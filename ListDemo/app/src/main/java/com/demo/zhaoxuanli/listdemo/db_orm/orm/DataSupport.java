package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.db_orm.StudentValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ORM 数据库操作类
 * <p>
 * Created by lizhaoxuan on 15/11/5.
 */
public class DataSupport implements IDataSupport {
    private static final String TAG = DataSupport.class.getName();
    private static SQLiteDatabase db;

    private static DataSupport instance = null;

    /*field缓存，避免重复获取*/
    private static FieldCache fieldCache;

    private static ArrayMap<Class, String> createTableSql = new ArrayMap<>();

    private DataSupport(Context context) {

        createTableSql.put(StudentValue.class, Strs.STUDENT);

        DatabaseHelper database = new DatabaseHelper(context);
        db = database.getWritableDatabase();
        fieldCache = new FieldCache();
    }

    public static DataSupport getInstance(Context context) {
        if (instance == null) {
            instance = new DataSupport(context);
        }
        return instance;
    }

    @Override
    public <T> void insertEntity(T object) {
        Class clazz = object.getClass();
        String tableName = fieldCache.getClassName(clazz);
        ArrayList<Field> fieldList = fieldCache.getFields(clazz);
        ArrayList<String> fieldNames = fieldCache.getFieldNames(clazz);
        ArrayList<String> types = fieldCache.getFieldTypes(clazz);
        //实例化一个ContentValues用来装载待插入的数据
        ContentValues cv = new ContentValues();
        try {
            int length = fieldList.size();
            for (int i = 0; i < length; i++) {
                fieldList.get(i).setAccessible(true);
                putValue(cv, types.get(i), fieldNames.get(i),
                        fieldList.get(i).get(object));
            }
            long i = db.insert(tableName, null, cv);//执行插入操作

            Log.i(TAG, object.getClass().getName() + " 插入结果 ： " + i);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {

        }
    }

    @Override
    public <T> void insertEntity(List<T> entityList) {
        if (entityList.isEmpty()) {
            return;
        }
        Class clazz = entityList.get(0).getClass();
        String tableName = fieldCache.getClassName(clazz);
        ArrayList<Field> fieldList = fieldCache.getFields(clazz);
        ArrayList<String> fieldNames = fieldCache.getFieldNames(clazz);
        ArrayList<String> types = fieldCache.getFieldTypes(clazz);
        int listSize = entityList.size();
        int fieldLength = fieldList.size();
        //实例化一个ContentValues用来装载待插入的数据
        ContentValues cv = new ContentValues();
        for (int position = 0; position < listSize; position++) {
            try {
                for (int i = 0; i < fieldLength; i++) {
                    fieldList.get(i).setAccessible(true);
                    putValue(cv, types.get(i), fieldNames.get(i),
                            fieldList.get(i).get(entityList.get(position)));
                }
                long result = db.insert(tableName, null, cv);//执行插入操作

                Log.i(TAG, entityList.get(position).getClass().getName() + " 插入结果 ： " + result);
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                cv.clear();
            }
        }
    }

    @Override
    public Object getEntity(int id, Class clazz) {
        String tableName = fieldCache.getClassName(clazz);
        ArrayList<Field> fieldList = fieldCache.getFields(clazz);
        ArrayList<String> fieldNames = fieldCache.getFieldNames(clazz);
        ArrayList<String> types = fieldCache.getFieldTypes(clazz);
        //初始化对象
        Object entity = ReflectionTool.createObject(clazz);

        Cursor cursor = db.query(tableName, null,
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        int length = fieldList.size();

        try {
            while (cursor.moveToNext()) {
                //开始给对象赋值
                for (int i = 0; i < length; i++) {
                    fieldList.get(i).setAccessible(true);
                    fieldList.get(i).set(entity, getValue(cursor, types.get(i), fieldNames.get(i)));
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            cursor.close();
        }

        return entity;
    }

    @Override
    public ArrayList getAllEntity(Class clazz) {
        String tableName = fieldCache.getClassName(clazz);
        ArrayList<Field> fieldList = fieldCache.getFields(clazz);
        ArrayList<String> fieldNames = fieldCache.getFieldNames(clazz);
        ArrayList<String> types = fieldCache.getFieldTypes(clazz);

        ArrayList result = new ArrayList();

        Cursor cursor = db.query(tableName, null, null, null, null, null, null, null);
        int length = fieldList.size();

        try {
            while (cursor.moveToNext()) {
                //初始化对象
                Object entity = ReflectionTool.createObject(clazz);
                //开始给对象赋值
                for (int i = 0; i < length; i++) {
                    fieldList.get(i).setAccessible(true);
                    fieldList.get(i).set(entity, getValue(cursor, types.get(i),
                            fieldNames.get(i)));
                }
                result.add(entity);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            cursor.close();
        }

        return result;
    }

    @Override
    public int deleteEntity(int id, Class clazz) {
        String tableName = fieldCache.getClassName(clazz);
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(tableName, whereClause, whereArgs);
    }


    @Override
    public <T> long changeEntity(int id, T object) {
        long result = 0;
        Class clazz = object.getClass();
        String tableName = fieldCache.getClassName(clazz);
        ArrayList<Field> fieldList = fieldCache.getFields(clazz);
        ArrayList<String> fieldNames = fieldCache.getFieldNames(clazz);
        ArrayList<String> types = fieldCache.getFieldTypes(clazz);

        //实例化一个ContentValues用来装载待插入的数据
        ContentValues cv = new ContentValues();
        try {
            int length = fieldList.size();
            for (int i = 0; i < length; i++) {
                putValue(cv, types.get(i), fieldNames.get(i),
                        fieldList.get(i).get(object));
            }
            result = db.update(tableName, cv, "id = ?", new String[]{String.valueOf(id)});
            Log.i(TAG, tableName + " 表插入结果" + result);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }


    /**
     * 数据库 put操作
     *
     * @param cv
     * @param type
     * @param name
     * @param object
     */
    private static void putValue(ContentValues cv, String type, String name, Object object) {
        if (type.contains("String")) {
            cv.put(name, (String) object);
        } else if (type.contains("int")) {
            cv.put(name, (int) object);
        } else if (type.contains("double")) {
            cv.put(name, (double) object);
        }
    }

    /**
     * 数据库 get操作
     *
     * @param cursor
     * @param type
     * @param name
     * @return
     */
    private static Object getValue(Cursor cursor, String type, String name) {
        if (type.contains("String")) {
            return cursor.getString(cursor.getColumnIndex(name));
        } else if (type.contains("int")) {
            return cursor.getInt(cursor.getColumnIndex(name));
        } else if (type.contains("double")) {
            return cursor.getDouble(cursor.getColumnIndex(name));
        } else {
            Log.w(TAG, "getValue 无合适类型，为Null了  " + type);
            return null;
        }
    }

    private boolean checkTableVersion() {
        return false;
    }

}
