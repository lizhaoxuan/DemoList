package com.demo.zhaoxuanli.listdemo.popup_tips;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;

import java.util.List;

/**
 * 空数据提示控件
 * 1.控件默认显示到界面中间，提供重载方法给予下移：下移一个标题栏高度
 * 2.提供文字显示和图片显示接口
 * 3.Bind 控件（List数据集或adapter等）,如果不为空就不会显示
 * Created by lizhaoxuan on 15/11/21.
 */
public class NoDataTip {
    public static int TitleHeight = 0;  //状态栏+标题栏高度 自动计算
    public static int StateHeight = 0;  //状态栏高度

    private PopupWindow popupWindow;
    private View popupWindow_view;
    private Activity activity;

    private ImageView imageView;
    private TextView textView;

    /**目标数据集，通过该数据集判断是否显示**/
    private List dataSet;

    public NoDataTip(Activity context, int drawableId, String tip) {
        this.activity = context;
        initView();
        imageView.setImageResource(drawableId);
        textView.setText(tip);
    }

    public NoDataTip(Activity context, Drawable drawable, String tip) {
        this.activity = context;
        initView();
        imageView.setImageDrawable(drawable);
        textView.setText(tip);
    }


    private void initView() {
        if (popupWindow_view == null) {
            // 获取自定义布局文件pop.xml的视图
            popupWindow_view = activity.getLayoutInflater().inflate(R.layout.widget_no_data_tip, null,
                    false);
        }
        // 创建PopupWindow实例 分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置popup动画效果
        popupWindow.setFocusable(false); // 设置popupwindow可获得焦点
        popupWindow.setTouchable(false); // 设置PopupWindow可触摸

        imageView = (ImageView) popupWindow_view.findViewById(R.id.imageView);
        textView = (TextView) popupWindow_view.findViewById(R.id.textView);
        initTipsData();
    }

    /**
     * 当TitleHeight ||  MenuWidth的值为0时，才重新获取。
     * 免去每次创建都计算Popup高度等信息，提高效率
     */
    private void initTipsData(){
        if(TitleHeight <=0 ||StateHeight == 0){
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
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

    public void show(View view) {
        if (popupWindow == null) {
            initView();
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void show(View view,int belowHeight) {
        if (popupWindow == null) {
            initView();
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, belowHeight);
    }
    public void hide() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void setText(String tip) {
        textView.setText(tip);
    }

    public void setImage(int id) {
        imageView.setImageResource(id);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void bindDataSet(List dataSet){
        this.dataSet = dataSet;
    }

    public void doNoDataTip(){
        if (dataSet.isEmpty()){

        }
    }

}
