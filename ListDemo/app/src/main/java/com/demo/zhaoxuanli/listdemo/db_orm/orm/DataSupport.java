package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.DemoApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * ORM 数据库操作类
 *
 * Created by lizhaoxuan on 15/11/5.
 */
public class DataSupport {

    private static  SQLiteDatabase db;

    private static DataSupport instance = null;

    private DataSupport(Context context){
        DatabaseHelper database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
        db = database.getWritableDatabase();
    }

    public static DataSupport getInstance(Context context){
        if(instance == null){
            instance = new DataSupport(context);
        }
        return instance;
    }

    public void BuildDataSupport(){
        new DataSupport(DemoApplication.getInstance());
    }

    public  <T>void insertEntity(T clazz){
        try {
            System.out.println("开始插入！！！");
            String tableName = clazz.getClass().getName();

            ArrayList<Field> fieldList = new ArrayList<>();
            ArrayList<String> types = new ArrayList<>();
            ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
            ReflectionTool.createProperty(clazz.getClass(),fieldList,types);

            int length = fieldList.size();
            for(int i = 0 ; i < length ; i++){
                fieldList.get(i).setAccessible(true);
                putValue(cv, types.get(i), fieldList.get(i).getName(), fieldList.get(i).get(clazz));
            }

            long i = db.insert("StudentValue",null,cv);//执行插入操作
            System.out.println("SQLiteActivity插入： " + i);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public  Object getEntity(int id,Class clazz){
        ArrayList<Field> fieldList = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ReflectionTool.createProperty(clazz,fieldList,types);
        Object entity = ReflectionTool.createObject(clazz);  //初始化对象
        Cursor c = db.query("StudentValue", null,
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        int length = fieldList.size();
        try {
            while (c.moveToNext()) {
                for(int i = 0 ; i < length ; i++){   //开始给对象赋值
                    fieldList.get(i).setAccessible(true);
                    fieldList.get(i).set(entity,getValue(c,types.get(i),fieldList.get(i).getName()));
                }
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        c.close();

        return entity;
    }


    public  <T> List<T> getListObject(Class clazz){

        return null;
    }

    public  int deleteEntity(int id,Class clazz){
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(clazz.getName(), whereClause, whereArgs);
    }

    public  <T> void changeEntity(int id,T clazz){
        try{
            String tableName = clazz.getClass().getName();

            ArrayList<Field> fieldList = new ArrayList<>();
            ArrayList<String> types = new ArrayList<>();
            ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
            ReflectionTool.createProperty(clazz.getClass(),fieldList,types);
            int length = fieldList.size();
            for(int i = 0 ; i < length ; i++){
                putValue(cv,types.get(i),fieldList.get(i).getName(),fieldList.get(i).get(clazz));
            }
            db.update(tableName, cv, "id = ?", new String[]{String.valueOf(id)});
        }catch (Exception e){
            e.getStackTrace();
        }
    }


    /**
     * 数据库 put操作
     * @param cv
     * @param type
     * @param name
     * @param object
     */
    private static void putValue(ContentValues cv,String type,String name,Object object){
        if(type.contains("String")){
            cv.put(name,(String)object);
        }else if(type.contains("int")){
            cv.put(name,(int)object);
        }else if(type.contains("double")){
            cv.put(name,(double)object);
        }
    }

    /**
     * 数据库 get操作
     * @param cursor
     * @param type
     * @param name
     * @return
     */
    private static Object getValue(Cursor cursor,String type,String name){
        if(type.contains("String")){
            return cursor.getString(cursor.getColumnIndex(name));
        }else if(type.contains("int")){
            return cursor.getInt(cursor.getColumnIndex(name));
        }else if(type.contains("double")){
            return cursor.getDouble(cursor.getColumnIndex(name));
        }else{
            System.out.println("为null了！！！");
            return null;
        }
    }

}
