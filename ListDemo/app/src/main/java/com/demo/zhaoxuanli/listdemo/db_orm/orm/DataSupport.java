package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.demo.zhaoxuanli.listdemo.DemoApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * ORM 数据库操作类
 *
 * Created by lizhaoxuan on 15/11/5.
 */
public class DataSupport {

    private static  SQLiteDatabase db;

    private DataSupport(Context context){
        DatabaseHelper database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
        db = database.getWritableDatabase();
    }

    public void BuildDataSupport(){
        new DataSupport(DemoApplication.getInstance());
    }

    public static <T>void save(T classT) throws Exception{
        ArrayList<Field> fieldList = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
        ReflectionTool.createProperty(classT.getClass(),fieldList,types);

        int length = fieldList.size();
        for(int i = 0 ; i < length ; i++){
            putValue(cv,types.get(i),fieldList.get(i).getName(),fieldList.get(i).get(classT));
        }

        db.insert("user",null,cv);//执行插入操作

    }

    public static <T> T getObject(int id,Class classT){

        return null;
    }


    public static <T> List<T> getListObject(Class classT){

        return null;
    }

    public static <T> T deleteObject(int id,Class classT){

        return null;
    }



    private static void putValue(ContentValues cv,String type,String name,Object object){

        if(type.contains("String")){
            cv.put(name,(String)object);
        }else if(type.contains("int")){
            cv.put(name,(int)object);
        }

    }

}
