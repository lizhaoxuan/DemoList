package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhaoxuan on 15/11/5.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydata.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
        
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table student_value(id INTEGER not null , name varchar(60),sex varchar(10),class_name varchar(50),school_name varchar(50));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
