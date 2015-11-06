package com.demo.zhaoxuanli.listdemo.db_orm;

/**
 * Created by lizhaoxuan on 15/11/5.
 */
public class StudentValue {

    private int id ;
    private String name;
    private String sex;
    private String className;
    private String schoolName;

    public StudentValue() {
    }

    public StudentValue(int id, String name, String sex, String className, String schoolName) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.className = className;
        this.schoolName = schoolName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
