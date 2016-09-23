package com.demo.zhaoxuanli.listdemo.router;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class Finder {
    private CakeRouter cakeRouter;

    public Finder(CakeRouter cakeRouter) {
        this.cakeRouter = cakeRouter;
    }

    /**
     * domain是否符合
     *
     * @param url url指令
     * @return false 不符合  true 符合
     */
    public void finderUrl(String url) throws RouterException {
        if (url == null || "".equals(url)) {
            throw new RouterException("url == null or url length = 0");
        }

        //获取并检查domain
        int index = checkDomain(url);

        //获取并检查pageName
        index = getPageName(index, url);
        if (index == -1 || index >= url.length()) {
            //无参数
            return;
        }

        //获取并检查参数
        getExtras(index, url);
    }

    /**
     * 获取url的中 domain
     *
     * @param url url
     * @return 下标 +3
     */
    private int checkDomain(String url) throws RouterException {
        int index = url.indexOf("://");
        if (index != -1) {
            String domain = url.substring(0, index);
            if (!cakeRouter.getDomain().equals(domain)) {
                throw new RouterException("Not the right domain name" + domain);
            }
        } else {
            throw new RouterException("Can't get domian from the url, url = " + url);
        }
        return index + 3;
    }


    /**
     * 获取pageName
     *
     * @param start page start index
     * @return page end index
     * @throws RouterException
     */
    private int getPageName(int start, String url) throws RouterException {
        if (start >= url.length()) {
            throw new RouterException("must be have pageName! url = " + url);
        }
        url = url.substring(start);
        start = url.indexOf("?");
        if (start == -1) {
            cakeRouter.setPageName(url);
        } else {
            cakeRouter.setPageName(url.split("&"));
            start += 1;
        }
        return start;
    }

    /**
     * 获取参数列表
     *
     * @param start extras start index
     * @throws RouterException
     */
    private void getExtras(int start, String url) throws RouterException {
        url = url.substring(start);
        String[] entityArray = url.split("&");
        for (String entityStr : entityArray) {
            String key, type, value;
            int typeIndex = entityStr.indexOf("@");
            int valueIndex = entityStr.indexOf("=");
            if (valueIndex == -1) {
                if (cakeRouter.getStrictModel()) {
                    throw new RouterException("parameter must have key and value! url=" + url);
                } else {
                    continue;
                }
            }
            if (typeIndex == -1) {
                //默认String
                key = url.substring(0, valueIndex);
                type = Type.STRING_KEY;
                value = url.substring(valueIndex + 1);
            } else {
                key = url.substring(0, typeIndex);
                type = url.substring(typeIndex + 1, valueIndex);
                value = url.substring(valueIndex + 1);
            }

            cakeRouter.addExtra(new Extra(key, type, value));
        }
    }

}
