package com.demo.zhaoxuanli.listdemo.distribute;

/**
 * Created by lizhaoxuan on 16/3/7.
 */
public class StudentDto {

    private String className ;

    private int age;

    public StudentDto(int age, String className) {
        this.age = age;
        this.className = className;
    }

    public int getAge() {
        return age;
    }

    public String getClassName() {
        return className;
    }
}
