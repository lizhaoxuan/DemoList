package com.demo.zhaoxuanli.listdemo.tradition_db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by lizhaoxuan on 16/7/4.
 */
public class StudentDao {
    protected SQLiteDatabase db = null;

    public StudentDao(SQLiteDatabase db) {
        this.db = db;
    }

    public static String createTable(boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        return "CREATE TABLE " + constraint + "\"STUDENT\" (" +
                "CAKEDAO_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "BIRTHDAY INTEGER," +
                "IS_SUCCESS INTEGER);";
    }

    public static String dropTable(boolean ifExists) {
        return "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STUDENT\"";
    }

    public long deleteAllData() {
        return db.delete("STUDENT", null, null);
    }

    public long deleteById(long id) {
        return db.delete("STUDENT", "CAKEDAO_ID = ?", new String[]{String.valueOf(id)});
    }

    public long deleteById(Student value) {
        return db.delete("STUDENT", "CAKEDAO_ID = ?", new String[]{String.valueOf(value.id)});
    }

    public long[] deleteByIds(long[] ids) {
        long[] result = new long[ids.length];
        db.beginTransaction();
        for (int i = 0, l = ids.length; i < l; i++) {
            result[i] = db.delete("STUDENT", "CAKEDAO_ID = ?", new String[]{String.valueOf(ids[i])});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public long delete(String whereClause, String[] whereArgs) {
        return db.delete("STUDENT", whereClause, whereArgs);
    }

    public Student[] loadDataById(long id) {
        Cursor results = db.query("STUDENT", null, "CAKEDAO_ID = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        return convertToValue(results);
    }

    public Student[] loadAllData() {
        Cursor results = db.query("STUDENT", null, null, null, null, null, null);
        return convertToValue(results);
    }

    public Student[] loadData(String whereClause, String[] whereArgs) {
        Cursor results = db.query("STUDENT", null, whereClause, whereArgs, null, null, null);
        return convertToValue(results);
    }

    public long insert(Student value) {
        String sql = "INSERT INTO STUDENT(NAME,BIRTHDAY,IS_SUCCESS) VALUES(?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, value.name);
        statement.bindLong(2, value.birthday.getTime());
        statement.bindLong(3, value.isSuccess ? 1L : 0L);
        return statement.executeInsert();
    }

    public long[] inserts(Student[] values) {
        long[] result = new long[values.length];
        db.beginTransaction();
        for (int i = 0, l = values.length; i < l; i++) {
            Student value = values[i];
            String sql = "INSERT INTO STUDENT(NAME,BIRTHDAY,IS_SUCCESS) VALUES(?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, value.name);
            statement.bindLong(2, value.birthday.getTime());
            statement.bindLong(3, value.isSuccess ? 1L : 0L);
            result[i] = statement.executeInsert();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public long updateById(long id, Student value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", value.name);
        contentValues.put("BIRTHDAY", value.birthday.getTime());
        contentValues.put("IS_SUCCESS", value.isSuccess ? 1L : 0L);
        return db.update("STUDENT", contentValues, "CAKEDAO_ID = " + id, null);
    }

    public long update(Student value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", value.name);
        contentValues.put("BIRTHDAY", value.birthday.getTime());
        contentValues.put("IS_SUCCESS", value.isSuccess ? 1L : 0L);
        return db.update("STUDENT", contentValues, "CAKEDAO_ID = " + value.id, null);
    }

    public long[] update(Student[] values) {
        long[] result = new long[values.length];
        db.beginTransaction();
        for (int i = 0, l = values.length; i < l; i++) {
            Student value = values[i];
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", value.name);
            contentValues.put("BIRTHDAY", value.birthday.getTime());
            contentValues.put("IS_SUCCESS", value.isSuccess ? 1L : 0L);
            result[i] = db.update("STUDENT", contentValues, "CAKEDAO_ID = " + value.id, null);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    private Student[] convertToValue(Cursor cursor) {
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Student[] values = new Student[count];
        for (int i = 0; i < count; i++) {
            values[i] = new Student();
            values[i].id = cursor.getInt(cursor.getColumnIndex("CAKEDAO_ID"));
            values[i].name = cursor.getString(cursor.getColumnIndex("NAME"));
            cursor.moveToNext();
        }
        cursor.close();
        return values;
    }

}
