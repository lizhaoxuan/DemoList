package com.demo.zhaoxuanli.listdemo.router;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class Tool {


    private Tool() {
    }

    public static Type convertorType(String type) {
        switch (type) {
            case Type.INT_KEY:
                return Type.INT;
            case Type.LONG_KEY:
                return Type.LONG;
            case Type.SHORT_KEY:
                return Type.SHORT;
            case Type.FLOAT_KEY:
                return Type.FLOAT;
            case Type.DOUBLE_KEY:
                return Type.DOUBLE;
            case Type.BYTE_KEY:
                return Type.BYTE;
            case Type.BOOLEAN_KEY:
                return Type.BOOLEAN;
            case Type.CHAR_KEY:
                return Type.CHAR;
            case Type.STRING_KEY:
                return Type.STRING;
            default:
                return null;
        }
    }

}
