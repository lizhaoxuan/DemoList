package com.demo.zhaoxuanli.listdemo.popup_tips;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;

import com.demo.zhaoxuanli.listdemo.R;

import java.lang.ref.SoftReference;


/**
 * Created by zhaoxuan.li on 2015/10/13.
 */
public class PopupTips  {

    private static int TitleHeight = 0;
    private static int MenuWidth =0;
    private static int MenuHeight = 100;
    private static int DisplayTime = 1500;
    private static SoftReference<AutoHideTipThread> softAutoThread ;
    private Activity activity;
    private View popupWindow_view;
    private PopupWindow popupWindow;

    public PopupTips(Activity activity){
        this.activity = activity;
    }

    private void initView(){

        initTipsData();

        // 获取自定义布局文件pop.xml的视图
        popupWindow_view = activity.getLayoutInflater().inflate(R.layout.widget_top_toast, null,
                false);
        // 创建PopupWindow实例 分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, MenuWidth, MenuHeight, true);
        //设置popup动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);

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
    }


    /**
     * 当TitleHeight ||  MenuWidth的值为0时，重新获取。
     * 免去每次创建都计算Popup高度等信息，提高效率
     */
    private void initTipsData(){
        if(TitleHeight ==0 || MenuWidth ==0){
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            MenuWidth = metric.widthPixels;     // 屏幕宽度（像素）
            Rect outRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
            TitleHeight = outRect.top + activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        }
        initAutoThread();
    }

    private void initAutoThread(){
        if(softAutoThread == null){
            AutoHideTipThread autoHideTipThread = new AutoHideTipThread();
            softAutoThread = new SoftReference<AutoHideTipThread>(autoHideTipThread);
        }
    }



    /**
     * DisplayTime 结束后，自动隐藏Tips
     */
    private void autoHideTip(){
        //autoHideTipThread.start();
    }

    /**
     * 自定义Popup高度，默认为100
     * @param height
     */
    public void setTipHeight(int height){
        MenuHeight = height;
    }

    /**
     * 设置 Tips的显示时间，如果 设为 -1 ，则表示不自动隐藏
     * @param displayTime
     */
    public void setDisplayTime(int displayTime){
        DisplayTime = displayTime;
    }

    /**
     * 显示PopupWindow。 如果Popup正在显示，便销毁。
     * Popup 显示 1.5秒
     */
    public void showPopupWindow(){
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initView();
        }
        popupWindow.showAtLocation(activity.findViewById(R.id.recyclerView), Gravity.TOP | Gravity.LEFT, 0, TitleHeight);

        if(DisplayTime == -1) //Popup不自动隐藏
            return ;
        else{
            autoHideTip();
        }
    }




    /**
     * 用于 Tips显示一段时候后自动隐藏的线程类
     */
    private class AutoHideTipThread extends Thread{
        private Handler handler = new Handler();
        @Override
        public void run() {
            super.run();
            try{
                Thread.sleep(DisplayTime);
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
