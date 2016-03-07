package com.demo.zhaoxuanli.listdemo.distribute;

/**
 * Created by lizhaoxuan on 16/3/7.
 */
public class UserDto {

    private int id;

    private String name;

    public UserDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
