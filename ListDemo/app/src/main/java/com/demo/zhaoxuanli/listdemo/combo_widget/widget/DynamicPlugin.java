package com.demo.zhaoxuanli.listdemo.combo_widget.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

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

    /**
     * View that handles event dispatch and content transitions.
     */
    private PopupDecorView mDecorView;


    private int mWindowLayoutType = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;


    private Activity mActivity;
    private WindowManager mWindowManager;
    private View mContentView;


    //标志位
    private boolean mIsShowing;

    public DynamicPlugin(Activity mActivity, View contentView) {
        this.mActivity = mActivity;
        mWindowManager = mActivity.getWindowManager();
        mContentView = contentView;
    }

    public void show() {
        if (isShowing() || mContentView == null) {
            return;
        }
        mIsShowing = true;

        final WindowManager.LayoutParams p = createPopupLayoutParams();
        p.gravity = Gravity.CENTER;
        p.x = 0;
        p.y = 0;
        //mDecorView = createDecorView(mContentView);

        invokePopup(p);
    }

    public void show(int y) {
        if (isShowing() || mContentView == null) {
            return;
        }
        mIsShowing = true;

        final WindowManager.LayoutParams p = createPopupLayoutParams();
        p.gravity = Gravity.CENTER;
        p.x = 50;
        p.y = y;
        //mDecorView = createDecorView(mContentView);

        invokePopup(p);
    }

    private WindowManager.LayoutParams createPopupLayoutParams() {
        final WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.flags = computeFlags(p.flags);
        p.type = mWindowLayoutType;
        p.token = mActivity.getWindow().getDecorView().getWindowToken();
        p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.format = PixelFormat.TRANSLUCENT;
        //p.windowAnimations = computeAnimationResource();
        return p;
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

    private PopupDecorView createDecorView(View contentView) {
        final ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
        final int height;
        if (layoutParams != null && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        final PopupDecorView decorView = new PopupDecorView(mActivity);
        decorView.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, height);
        decorView.setClipChildren(false);
        decorView.setClipToPadding(false);

        return decorView;
    }

    private void invokePopup(WindowManager.LayoutParams p) {
        if (mActivity != null) {
            p.packageName = mActivity.getPackageName();
        }

//        final PopupDecorView decorView = mDecorView;
//        decorView.setFitsSystemWindows(false);

        //setLayoutDirectionFromAnchor();

        mWindowManager.addView(mContentView, p);

//        if (mEnterTransition != null) {
//            decorView.requestEnterTransition(mEnterTransition);
//        }
    }

//    private void setLayoutDirectionFromAnchor() {
//        View anchor = mActivity.getWindow().getDecorView();
//        if (anchor != null) {
//            mDecorView.setLayoutDirection(anchor.getLayoutDirection());
//        }
//    }

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

    private class PopupDecorView extends FrameLayout {
        //private TransitionListenerAdapter mPendingExitListener;

        public PopupDecorView(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(event);
                }

                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null && state.isTracking(event) && !event.isCanceled()) {
                        // dismiss();
                        return true;
                    }
                }
                return super.dispatchKeyEvent(event);
            } else {
                return super.dispatchKeyEvent(event);
            }
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
//            if (mTouchInterceptor != null && mTouchInterceptor.onTouch(this, ev)) {
//                return true;
//            }
            return super.dispatchTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if ((event.getAction() == MotionEvent.ACTION_DOWN)
                    && ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))) {
                //dismiss();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                //dismiss();
                return true;
            } else {
                return super.onTouchEvent(event);
            }
        }


    }

}
