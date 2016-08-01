package com.demo.zhaoxuanli.listdemo.tradition_db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Collection;

/**
 * Created by lizhaoxuan on 16/7/7.
 */
public class ClassesDao {

    protected SQLiteDatabase db = null;

    public ClassesDao(SQLiteDatabase db) {
        this.db = db;
    }

    public static String createTable(boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        return "CREATE TABLE " + constraint + "\"CLASSES\" (" +
                "CAKEDAO_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CLASS_NAME TEXT," +
                "STUDENT_NUM INTEGER);";
    }

    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CLASSES\"";
    }

    public long deleteAllData() {
        return db.delete("CLASSES", null, null);
    }

    public long deleteById(long id) {
        return db.delete("CLASSES", "CAKEDAO_ID = ?", new String[]{String.valueOf(id)});
    }

    public long deleteById(Classes value) {
        return db.delete("CLASSES", "CAKEDAO_ID = ?", new String[]{String.valueOf(value.id)});
    }

    public long[] deleteByIds(long[] ids) {
        long[] result = new long[ids.length];
        db.beginTransaction();
        for (int i = 0, l = ids.length; i < l; i++) {
            result[i] = db.delete("CLASSES", "CAKEDAO_ID = ?", new String[]{String.valueOf(ids[i])});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public long delete(String whereClause, String[] whereArgs) {
        return db.delete("CLASSES", whereClause, whereArgs);
    }

    public Classes[] loadDataById(long id) {
        Cursor results = db.query("STUDENT", null, "CAKEDAO_ID = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        return convertToValue(results);
    }

    public Classes[] loadAllData() {
        Cursor results = db.query("CLASSES", null, null, null, null, null, null);
        return convertToValue(results);
    }

    public Classes[] loadData(String whereClause, String[] whereArgs) {
        Cursor results = db.query("CLASSES", null, whereClause, whereArgs, null, null, null);
        return convertToValue(results);
    }

    public long insert(Classes value) {
        String sql = "INSERT INTO CLASSES(CLASS_NAME,STUDENT_NUM) VALUES(?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, value.className);
        statement.bindLong(2, value.studentNum);
        return statement.executeInsert();
    }

    public long[] inserts(Classes[] values) {
        long[] result = new long[values.length];
        db.beginTransaction();
        for (int i = 0, l = values.length; i < l; i++) {
            Classes value = values[i];
            String sql = "INSERT INTO CLASSES(CLASS_NAME,STUDENT_NUM) VALUES(?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, value.className);
            statement.bindLong(2, value.studentNum);

            result[i] = statement.executeInsert();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public long updateById(long id, Classes value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLASS_NAME", value.className);
        contentValues.put("STUDENT_NUM", value.studentNum);
        return db.update("CLASSES", contentValues, "CAKEDAO_ID = " + id, null);
    }

    public long update(Classes value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLASS_NAME", value.className);
        contentValues.put("STUDENT_NUM", value.studentNum);
        return db.update("CLASSES", contentValues, "CAKEDAO_ID = " + value.id, null);
    }

    public long[] update(Classes[] values) {
        long[] result = new long[values.length];
        db.beginTransaction();
        for (int i = 0, l = values.length; i < l; i++) {
            Classes value = values[i];
            ContentValues contentValues = new ContentValues();
            contentValues.put("CLASS_NAME", value.className);
            contentValues.put("STUDENT_NUM", value.studentNum);
            result[i] = db.update("CLASSES", contentValues, "CAKEDAO_ID = " + value.id, null);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    private Classes[] convertToValue(Cursor cursor) {
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Classes[] values = new Classes[count];
        for (int i = 0; i < count; i++) {
            values[i] = new Classes();
            values[i].id = cursor.getInt(cursor.getColumnIndex("CAKEDAO_ID"));
            values[i].className = cursor.getString(cursor.getColumnIndex("CLASS_NAME"));
            values[i].studentNum = cursor.getInt(cursor.getColumnIndex("STUDENT_NUM"));

            cursor.moveToNext();
        }
        cursor.close();
        return values;
    }

}
