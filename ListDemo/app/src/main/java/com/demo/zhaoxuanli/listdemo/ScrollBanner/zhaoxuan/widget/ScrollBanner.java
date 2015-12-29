package com.demo.zhaoxuanli.listdemo.ScrollBanner.zhaoxuan.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.zhaoxuanli.listdemo.R;

import java.lang.ref.WeakReference;

/**
 * Created by lizhaoxuan on 15/12/25.
 */
public class ScrollBanner extends LinearLayout {
    private static final int WHEEL_SHOW = 0;

    public static final int ORIENTATION_VERTICAL = 0;
    public static final int ORIENTATION_HORIZONTAL = 1;

    //banner高度
    private int mBannerHeight = 0;

    //banner自动滚动模式 默认垂直
    private int mOrientation = ORIENTATION_VERTICAL;

    //适配器
    private ScrollBannerAdapter mAdapter;

    //滑动监听
    private GestureDetector mGesture = null;

    //banner点击事件接口
    private OnBannerClickListener mBannerClickListener;

    //banner滑动时间
    private OnBannerScrollTouchListener mBannerScrollTouchListener;

    //CustomBannerItem滚播列表
    private View[] mScrollBanners;

    //banner 数据轮播标志位
    private int mShowPosition = 0;

    //banner View轮播标志位
    private int mViewPosition = 0;

    //是否正在轮播
    private boolean mIsWheelShowing = false;

    private BannerHandler mHandler;

    public ScrollBanner(Context context) {
        super(context);
        init(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.setOrientation(VERTICAL);

        mHandler = new BannerHandler(this);
        mGesture = new GestureDetector(context, new GestureListener());
        if (attrs == null) {
            mBannerHeight = (int) context.getResources().getDimension(R.dimen.banner_width);
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollBanner);
        mBannerHeight = (int) a.getDimension(R.styleable.ScrollBanner_banner_height, context.getResources().getDimension(R.dimen.banner_width));
        mOrientation = a.getInt(R.styleable.ScrollBanner_orientation, ORIENTATION_VERTICAL);
        a.recycle();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBannerHeight);
        this.setLayoutParams(layoutParams);
    }

    public void setAdapter(ScrollBannerAdapter adapter) {
        mAdapter = adapter;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBannerHeight);
        if (mScrollBanners != null) {
            return;
        }
        mScrollBanners = new View[]{mAdapter.getView(0, null), mAdapter.getView(0, null)};
        this.addView(mScrollBanners[0], layoutParams);
        this.addView(mScrollBanners[1], layoutParams);
        mShowPosition = (mShowPosition + 1) % mAdapter.getCount();
        mScrollBanners[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerClickListener != null) {
                    mBannerClickListener.onClick(mShowPosition);
                }
            }
        });
        mScrollBanners[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerClickListener != null) {
                    mBannerClickListener.onClick(mShowPosition);
                }
            }
        });
        mScrollBanners[0].setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGesture.onTouchEvent(event);
            }
        });
        mScrollBanners[0].setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGesture.onTouchEvent(event);
            }
        });

    }

    public void setBannerScrollTouchListener(OnBannerScrollTouchListener listener) {
        mBannerScrollTouchListener = listener;
    }

    public void setBannerClickListener(OnBannerClickListener listener) {
        mBannerClickListener = listener;
    }

    /**
     * 开始轮播展示
     */
    public void start() {
        if (mAdapter == null || mAdapter.getCount() <= 0) {
            hideCustomBanner();
            return;
        } else if (mIsWheelShowing) {
            //如果正在进行轮播，就不能再发出开始轮播命令
            return;
        }
        showCustomBanner();
        int time = mAdapter.getWheelTime(0);
        mHandler.removeCallbacks(delayRunable);
        mHandler.postDelayed(delayRunable, time);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        mIsWheelShowing = false;
        mHandler.removeCallbacks(delayRunable);
        if ((mAdapter == null || mAdapter.getCount() <= 0)) {
            hideCustomBanner();
        }
    }

    /**
     * 显示下一个
     */
    public void next() {
        nextOfModel(mOrientation);
    }

    public void next(int position) {
        nextOfModel(position, mOrientation);
    }

    /**
     * 根据给定模式显示下一个
     *
     * @param orientation 垂直 or 水平
     */
    public void nextOfModel(int orientation) {
        mScrollBanners[1 - mViewPosition] = mAdapter.getView(mShowPosition, mScrollBanners[1 - mViewPosition]);
        showNext(orientation);
        mShowPosition = (mShowPosition + 1) % mAdapter.getCount();
    }

    public void nextOfModel(int position, int orientation) {
        mScrollBanners[1 - mViewPosition] = mAdapter.getView(position, mScrollBanners[1 - mViewPosition]);
        showNext(orientation);
        mShowPosition = (position + 1) % mAdapter.getCount();
    }

    /**
     * 立刻显示单一Banner
     * 并取消滚动
     */
    public void fixBanner(int position) {
        mIsWheelShowing = false;
        mHandler.removeCallbacks(delayRunable);
        mScrollBanners[1 - mViewPosition] = mAdapter.getView(position, mScrollBanners[1 - mViewPosition]);
        showOnlyOne();
        mShowPosition = (position + 1) % mAdapter.getCount();
    }

    /**
     * 轮播显示下一个
     */
    private void scrollNext() {
        mIsWheelShowing = true;
        mScrollBanners[1 - mViewPosition] = mAdapter.getView(mShowPosition, mScrollBanners[1 - mViewPosition]);  //.setBannerData(bannerDatas.get((mShowPosition + 1) % showSize));

        showNext(mOrientation);

        //返回下一个banner的保持时间
        int time = mAdapter.getWheelTime(mShowPosition);
        mHandler.removeCallbacks(delayRunable);
        mHandler.postDelayed(delayRunable, time);

        mShowPosition = (mShowPosition + 1) % mAdapter.getCount();
    }

    /**
     * 显示先一个
     *
     * @param orientation 水平 or 垂直
     */
    private void showNext(int orientation) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            startHorizontalAnimation(mScrollBanners[1 - mViewPosition], mScrollBanners[mViewPosition]);
        } else if (orientation == ORIENTATION_VERTICAL) {
            startVerticalAnimation(mScrollBanners[1 - mViewPosition], mScrollBanners[mViewPosition]);
        }

        mViewPosition = 1 - mViewPosition;
    }

    /**
     * 只显示一个BannerItem
     */
    private void showOnlyOne() {
        startOnlyAnimation(mScrollBanners[1 - mViewPosition], mScrollBanners[mViewPosition]);
        mViewPosition = 1 - mViewPosition;
    }

    /**
     * 垂直滚动动画
     *
     * @param v1 待显示View
     * @param v2 当前View
     */
    private void startVerticalAnimation(View v1, View v2) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v1,
                "y", -mBannerHeight, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v1,
                "x", 0f, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v2,
                "y", 0f, mBannerHeight);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v2,
                "x", 0f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4);
        animSet.setDuration(500);
        animSet.start();
    }

    /**
     * 水平滚动动画
     *
     * @param v1 待显示View
     * @param v2 当前View
     */
    private void startHorizontalAnimation(View v1, View v2) {
        int width = this.getWidth();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v1,
                "y", 0f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v1,
                "x", -width, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v2,
                "y", 0f, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v2,
                "x", 0f, width);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4);
        animSet.setDuration(500);
        animSet.start();
    }

    private void startOnlyAnimation(View v1, View v2) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v1,
                "y", 0f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v1,
                "x", 0f, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v2,
                "y", mBannerHeight, mBannerHeight);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v2,
                "x", 0f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4);
        animSet.setDuration(500);
        animSet.start();
    }

    protected boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public void showCustomBanner() {
        if (!isShowing()) {
            this.setVisibility(VISIBLE);
        }
    }

    public void hideCustomBanner() {
        mIsWheelShowing = false;
        mHandler.removeCallbacks(delayRunable);
        if (isShowing()) {
            this.setVisibility(GONE);
        }
    }

    /**
     * banner点击事件
     */
    public interface OnBannerClickListener {
        void onClick(int position);
    }

    /**
     * banner点击事件
     */
    public interface OnBannerScrollTouchListener {
        void onScrollTouch(int position, MotionEvent e1, MotionEvent e2,
                           float distanceX, float distanceY);
    }

    private Runnable delayRunable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(WHEEL_SHOW);
        }
    };

    private static class BannerHandler extends Handler {
        private WeakReference<ScrollBanner> weakReference;

        public BannerHandler(ScrollBanner banner) {
            this.weakReference = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            ScrollBanner banner = weakReference.get();
            if (banner == null) {
                return;
            }
            if (msg.what == WHEEL_SHOW) {
                if (banner.mAdapter.getCount() <= 0) {
                    banner.hideCustomBanner();
                    return;
                } else {
                    banner.showCustomBanner();
                }
                if (banner.mAdapter.getCount() == 1) {
                    banner.showOnlyOne();
                } else {
                    banner.scrollNext();
                }
            }
        }
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mBannerScrollTouchListener != null) {
                mBannerScrollTouchListener.onScrollTouch(mShowPosition, e1, e2, distanceX, distanceY);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
