package com.demo.zhaoxuanli.listdemo.custom_widget.popup_tips;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;


/**
 * 顶部Toast提示控件
 * zhaoxuan.li
 * 2015/10/21.
 */
public class TopToast implements Cloneable {

    public static int TitleHeight = 0;  //状态栏+标题栏高度 自动计算
    public static int StateHeight = 0;  //状态栏高度
    private int menuWidth =0;     //自动计算
    private int menuHeight = 100;  //默认
    private int displayTime = 1800;  //可设置
    private Activity activity;
    private View popupWindow_view;
    private PopupWindow popupWindow;
    private TextView tipsText;
    private String tips;

    private static TopToast topToast ;

    private TopToast(Activity activity, String tips){
        this.tips = tips;
        this.activity = activity;
        initView(tips);
    }

    /**
     * 创建TopToast
     * @param activity
     * @param tips
     * @return
     */
    public static TopToast makeText(Activity activity , String tips){
        if(topToast == null){
            topToast = new TopToast(activity,tips);
            topToast.activity = null; // 使用结束后activity置为null, 避免抓住activity引用导致activity无法销毁
        }else{
            topToast.setTips(tips);
        }
        try {
            return (TopToast)topToast.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return topToast;
        }
    }

    /**
     * 创建 topToast
     * @param activity
     * @param tips
     * @param displayTime   TopToast显示时间
     * @return
     */
    public static TopToast makeText(Activity activity, String tips , int displayTime){
        TopToast tt = makeText(activity,tips);
        tt.displayTime = displayTime;
        return tt;
    }

    /**
     * 显示时，需要传入一个View来对当前主页面定位，什么View都可以
     * 默认现在在标题栏下方
     * @param view
     */
    public  void showPopupWindow(View view){
        if(popupWindow == null){
            initView(tips);
        }
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, 0, TitleHeight);
        autoHideTip();
    }
    /**
     * 显示时，需要传入一个View来对当前主页面定位，什么View都可以
     * 默认现在在标题栏下方
     * @param view  需要View来对当前主页面定位
     */
    public  void showPopupWindow(View view , int belowWhere){
        if(popupWindow == null){
            initView(tips);
        }
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, 0, belowWhere);
        autoHideTip();
    }


    /**
     * 当TitleHeight ||  MenuWidth的值为0时，才重新获取。
     * 免去每次创建都计算Popup高度等信息，提高效率
     */
    private void initTipsData(){
        if(TitleHeight <=0 || menuWidth ==0||StateHeight == 0){
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            menuWidth = metric.widthPixels;     // 屏幕宽度（像素）
            Rect outRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
            StateHeight = outRect.top;

            TypedValue tv = new TypedValue();
            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            {
                TitleHeight =outRect.top + TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
            //activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  //获取普通标题栏大小


        }
    }

    /**
     * 初始化 View
     * @param tips
     */
    private void initView(String tips){

        initTipsData();

        if(popupWindow_view == null){
            // 获取自定义布局文件pop.xml的视图
            popupWindow_view = activity.getLayoutInflater().inflate(R.layout.widget_top_toast, null,
                    false);
        }
        // 创建PopupWindow实例 分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, menuWidth, menuHeight, true);
        //设置popup动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        popupWindow.setFocusable(false);
        //点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });

        tipsText =  (TextView) popupWindow_view.findViewById(R.id.tipsText);
        tipsText.setText(tips);
    }


    /**
     * DisplayTime 结束后，自动隐藏Tips
     */
    private void autoHideTip(){
        if(displayTime == -1)
            return ;
        new AutoHideTipThread().start();
    }

    private void setTips(String tips){
        tipsText.setText(tips);
    }



    /**
     * 用于 Tips显示一段时候后自动隐藏的线程类
     */
    private class AutoHideTipThread extends Thread {
        private Handler handler = new Handler();
        @Override
        public void run() {
            super.run();
            try{
                Thread.sleep(displayTime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != popupWindow)
                            popupWindow.dismiss();
                    }
                });
            }catch (Exception e){}
        }
    }

}
