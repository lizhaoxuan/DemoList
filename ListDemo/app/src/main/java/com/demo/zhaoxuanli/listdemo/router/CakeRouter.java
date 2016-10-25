package com.demo.zhaoxuanli.listdemo.router;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaoxuan on 16/9/22.
 */
public class CakeRouter {
    public static final String TAG = CakeRouter.class.getSimpleName();

    private static CakeRouter instance;

    private static CakeRouter init(String domain) {
        instance = new CakeRouter(domain);
        return instance;
    }

    public static CakeRouter getInstance() {
        if (instance == null) {
            throw new NullPointerException("CakeRouter must be init from Builder");
        }
        return instance;
    }

    private CakeRouter(String domain) {
        this.domain = domain;
    }

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
     * 是否是严格模式
     * 严格模式将对url格式严格判断,对格式不符合的url 抛出异常
     * 非严格模式下，将会忽略部分不合格的参数,依然会尝试启动页面
     * true 严格模式  false 非严格模式，默认false
     */
    private boolean strictModel;

    /**
     * 页面名
     * 若有多个，将依次尝试跳转，若有多条均符合，android默认跳转第一个.
     */
    private String[] pageName;

    private List<Extra> extraList;

    String getDomain() {
        return domain;
    }

    void setPageName(String... pageName) {
        int length = pageName.length;
        this.pageName = new String[length];
        for (int i = 0; i < length; i++) {
            try {
                if (checkBlackList(this.pageName[i])) {
                    if (checkwhiteList(this.pageName[i])) {
                        this.pageName[i] = Tool.decode(pageName[i]);
                    } else {
                        break;
                    }
                } else {
                    this.pageName[i] = Tool.decode(pageName[i]);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    boolean getStrictModel() {
        return strictModel;
    }

    void addExtra(Extra extra) {
        if (extraList == null) {
            extraList = new ArrayList<>();
        }
        extraList.add(extra);
    }

    public Intent createIntent(Context context, String url) throws RouterException {
        Finder finder = new Finder(this);
        finder.finderUrl(url);

        Intent intent = new Intent();
        Class clazz = null;
        for (String page : pageName) {
            try {
                clazz = Class.forName(page);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (clazz == null) {
            Log.w(TAG, "page not found or is not Activity" + pageName);
            return null;
        } else {
            intent.setClass(context, clazz);
            if (extraList != null) {
                for (Extra extra : extraList) {
                    try {
                        extra.putExtra(intent);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (context instanceof Application) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        return intent;
    }

    public boolean dispatch(Context context, String url) {
        Intent intent = null;
        try {
            intent = createIntent(context, url);
        } catch (RouterException e) {
            e.printStackTrace();
            return false;
        }

        if (intent == null) {
            return false;
        }
        context.startActivity(intent);
        return true;
    }

    private boolean checkBlackList(String pageName) {
        if (blackList != null) {
            for (String name : blackList) {
                if (pageName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkwhiteList(String pageName) {
        if (whiteList != null) {
            for (String name : whiteList) {
                if (pageName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static class Builder {
        private CakeRouter cakeRouter;

        public Builder(String domain) {
            cakeRouter = CakeRouter.init(domain);
        }

        public Builder setBlackList(String... blackList) {
            cakeRouter.blackList = blackList;
            return this;
        }

        public Builder setWhiteList(String... whiteList) {
            cakeRouter.whiteList = whiteList;
            return this;
        }

        public Builder setStrictModel(boolean strictModel) {
            cakeRouter.strictModel = strictModel;
            return this;
        }

        public CakeRouter build() {
            return cakeRouter;
        }

    }
}
