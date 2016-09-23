package com.demo.zhaoxuanli.listdemo.router;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class CakeRouter {
    public static final String TAG = CakeRouter.class.getSimpleName();

    private static volatile CakeRouter instance;

    /**
     * 域名头 例如：eleme
     */
    private String domain;

    /**
     * 黑名单，黑名单中的页面不会被跳转
     * 包名 or 包名+类名
     */
    private String[] blackList;

    /**
     * 白名单,黑名单基础上的白名单
     * 包名+类名
     * 白名单优先级>黑名单。
     */
    private String[] whiteList;

    /**
     * Android activity 类名
     */
    private String activityName;

    /**
     * IOS Controller 名
     */
    private String controllerName;


    public String getDomain() {
        return domain;
    }
}
