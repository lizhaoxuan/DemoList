package com.demo.zhaoxuanli.listdemo.combo_widget.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * 动态插件
 * Created by lizhaoxuan on 16/1/18.
 */
public class DynamicPlugin {

    /**
     * 是否可持有焦点
     */
    private boolean mFocusable = false;

    /**
     * 是否可触的
     */
    private boolean mTouchable = true;

    /**
     * 设置是否忽略“脸颊触碰”，默认为false，即不忽略。
     * 什么是“脸颊触碰”?
     * Events都有一个大小，当Events的大小比手指尺寸大时即为CheekPress，
     * 这个可能常用于打电话时脸颊碰到屏幕的情况
     */
    private boolean mIgnoreCheekPress = false;

    /**
     * view范围外是否可触
     */
    private boolean mOutsideTouchable = false;

    /**
     * layout 是否在整个屏幕上
     * 猜测： 覆盖到状态栏
     */
    private boolean mLayoutInScreen;


    private int mWindowLayoutType = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;


    private Activity mActivity;
    private WindowManager mWindowManager;
    private View mContentView;


    //标志位
    private boolean mIsShowing;

    public DynamicPlugin(Activity mActivity, View contentView) {
        this.mActivity = mActivity;
        mWindowManager = mActivity.getWindowManager();
    }

    public void show() {
        if (isShowing() || mContentView == null) {
            return;
        }
        mIsShowing = true;

        final WindowManager.LayoutParams p = createPopupLayoutParams();

    }

    private WindowManager.LayoutParams createPopupLayoutParams() {
        final WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.flags = computeFlags(p.flags);
        p.type = mWindowLayoutType;
        p.token = mActivity.getWindow().getDecorView().getWindowToken();
        //p.windowAnimations = computeAnimationResource();
        return p;
    }

    public void show(int y) {

    }

    private int computeFlags(int curFlags) {
        curFlags &= ~(
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH);
        if (mIgnoreCheekPress) {
            curFlags |= WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
        }
        if (!mFocusable) {
            curFlags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        if (!mTouchable) {
            curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
        if (mOutsideTouchable) {
            curFlags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        }
        if (mLayoutInScreen) {
            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }
//        if (!mClippingEnabled) {
//            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
//        }
//        if (isSplitTouchEnabled()) {
//            curFlags |= WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
//        }
//        if (mLayoutInsetDecor) {
//            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
//        }
//        if (mNotTouchModal) {
//            curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        }
//        if (mAttachedInDecor) {
//            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR;
//        }
        return curFlags;
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void setmIgnoreCheekPress(boolean mIgnoreCheekPress) {
        this.mIgnoreCheekPress = mIgnoreCheekPress;
    }

    public boolean ismIgnoreCheekPress() {
        return mIgnoreCheekPress;
    }

    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }

    public boolean isOutsideTouchable() {
        return mOutsideTouchable;
    }

    public boolean isLayoutInScreenEnabled() {
        return mLayoutInScreen;
    }

    public void setLayoutInScreenEnabled(boolean enabled) {
        mLayoutInScreen = enabled;
    }


}
