package com.demo.zhaoxuanli.listdemo.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhaoxuan on 16/9/16.
 */
public class MergeRequestMap {

    public Map<Class, Object> result;

    public MergeRequestMap() {

        result = new HashMap<>();
        result.put(One.class, new One("one"));
        result.put(Two.class, new One("Two"));
        result.put(Three.class, new One("Three"));
        result.put(Four.class, new One("Four"));

    }

    public <T> T get() {
        for (Object value : result.values()) {
            try {
                return (T) value;
            } catch (ClassCastException e) {
                //next
            }
        }
        return null;
    }


}
