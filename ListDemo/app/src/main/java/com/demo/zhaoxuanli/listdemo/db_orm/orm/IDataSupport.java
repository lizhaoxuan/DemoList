package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaoxuan on 15/11/10.
 */
public interface IDataSupport {

    <T> void insertEntity(T entity);

    <T> void insertEntity(List<T> entityList);

    <T> T getEntity(int id ,Class clazz);

    ArrayList getAllEntity(Class clazz);

    <T> int deleteEntity(int id,Class clazz);

    <T> long changeEntity(int id,T entity);

}
