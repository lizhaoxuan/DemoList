package com.demo.zhaoxuanli.listdemo.router;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class Finder {
    private CakeRouter cakeRouter;

    private String domain = "";

    private String page = new StringBuilder();

    private StringBuilder controllerName = new StringBuilder();

    private List<Entity> entityList = new ArrayList<>();

    private int domainLength;

    public Finder(CakeRouter cakeRouter) {
        this.cakeRouter = cakeRouter;
        domainLength = cakeRouter.getDomain().length();
    }

    /**
     * domain是否符合
     *
     * @param url url指令
     * @return false 不符合  true 符合
     */
    public boolean finderUrl(String url) throws Exception {
        if (url == null || "".equals(url)) {
            Log.d(CakeRouter.TAG, "url == null or url length = 0");
            return false;
        }
        //检查domian
        int start = getDomain(url);
        if (!cakeRouter.getDomain().equals(domain)) {
            Log.d(CakeRouter.TAG, "Not the right domain name" + domain);
            return false;
        }


        if (!getPageName(start,url)){
            return false;
        }
        //获取pageName


        getPageName(url.substring(start, end));


        return true;
    }

    /**
     * 获取url的中 domain
     *
     * @param url url
     * @return 下标 +3
     */
    private int getDomain(String url) {
        int index = url.indexOf("://");
        if (index != -1) {
            domain = url.substring(0, index);
        }
        return index + 3;
    }

    private boolean getPageName(int start, String url) {
        if (start > url.length()) {
            Log.d(CakeRouter.TAG, "Not found the pageName");
            return false;
        }
        url = url.substring(start);
        int end = url.indexOf("?");
        if (end == -1) {
            Log.d(CakeRouter.TAG, "Not found the pageName");
            return false;
        }


        return true;
    }


    class Entity {
        StringBuilder key;
        StringBuilder type;
        StringBuilder value;
    }

}
