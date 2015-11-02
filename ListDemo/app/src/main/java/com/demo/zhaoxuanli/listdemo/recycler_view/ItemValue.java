package com.demo.zhaoxuanli.listdemo.recycler_view;

/**
 * Created by zhaoxuan.li on 2015/10/12.
 */
public class ItemValue {

    private int id ;
    private String title;
    private String explain;
    private Class class_t;

    public ItemValue(){}

    public ItemValue(int id , String title , String explain,Class class_t){
        this.id = id;
        this.title = title;
        this.explain = explain;
        this.class_t = class_t;
    }

    public Class getClass_t() {
        return class_t;
    }

    public void setClass_t(Class class_t) {
        this.class_t = class_t;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
