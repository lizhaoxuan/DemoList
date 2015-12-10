package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhaoxuan on 15/11/5.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_db"; //数据库名称
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

    /**
     * 当 VERSION 变化时调用此方法
     * 数据库的更新策略：每增加或修改一个表，必须在 onUpgrade 方法中增加该表的删除操作，
     * 避免同名表的重复创建。
     * db.execSQL("DROP TABLE IF EXISTS 新增表名"); // 如果存在，删除该表。
     * 为兼容旧版本，onUpgrade 方法内应存在过去所有历史表的删除操作。（无需考虑oldVersion）
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        /**如果存在，删除该表**/
        db.execSQL("DROP TABLE IF EXISTS student_value");

        /**创建新表**/
        onCreate(db);
    }
}
