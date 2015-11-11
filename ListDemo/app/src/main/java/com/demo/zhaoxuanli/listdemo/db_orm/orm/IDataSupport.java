package com.demo.zhaoxuanli.listdemo.db_orm.orm;

import java.util.List;

/**
 * Created by lizhaoxuan on 15/11/10.
 */
public interface IDataSupport {

    <T> void insertEntity(T entity);

    <T> void insertEntity(List<T> entityList);

    <T> T getEntity(int id ,Class clazz);

    <T> List<T> getAllEntity(Class clazz);

    <T> void deleteEntity(int id,Class clazz);

    <T> void changeEntity(int id,T entity);

}
