package com.demo.zhaoxuanli.listdemo.embed;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * Created by lizhaoxuan on 15/12/4.
 */
public class PackageHelper {

    /*上下文，创建view的时候需要用到*/
    private Context context;

    /*base view*/
    private FrameLayout contentView;

    /*base LinearLayout*/
    private LinearLayout contentLinearLayout;

    /*用户定义的view*/
    private View userView;

    /*toolbar*/
    private Toolbar toolBar;

    /*视图构造器*/
    private LayoutInflater inflater;

    /*集成控件*/
    private ViewGroup topTipsLayout;

    private ViewGroup noDataTipsLayout;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public PackageHelper(Context context, int layoutId) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        /*初始化整个内容*/
        initContentView();
        /*初始化各个预定组件，用户可自定义修改*/
        initWidget();

        /*添加线性布局 目的：添加TopToast*/
        initLinearLayout();

        /*初始化用户定义的布局*/
        initUserView(layoutId);
        /*添加空数据提示控件*/
        addNoDataTips();
        /*初始化toolbar*/
        initToolBar();


    }

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        contentView = new FrameLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);

    }

    private void initWidget(){
        this.topTipsLayout = new TopToast(context);
        this.noDataTipsLayout = new NoDataTips(context);
    }

    private void initLinearLayout(){
        contentLinearLayout = new LinearLayout(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,(int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = toolBarSize;

        contentLinearLayout.setLayoutParams(params);
        contentLinearLayout.setOrientation(LinearLayout.VERTICAL);

        contentView.addView(contentLinearLayout);

        //添加TopTips
        LinearLayout.LayoutParams topTipsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
        topTipsParams.topMargin = -50;
        contentLinearLayout.addView(topTipsLayout,topTipsParams);
        //topTipsLayout.setVisibility(View.GONE);

    }

    private void addNoDataTips(){
        ViewGroup.LayoutParams topTipsParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        noDataTipsLayout.setVisibility(View.GONE);
        contentView.addView(noDataTipsLayout);
    }

    private void initUserView(int id) {
        userView = inflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,(int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
//        params.topMargin = overly ? 0 : toolBarSize;
        params.topMargin = toolBarSize;
        contentLinearLayout.addView(userView, params);

    }

    private void initToolBar() {
        /*通过inflater获取toolbar的布局文件*/
        View toolbar = inflater.inflate(R.layout.widget_toolbar, contentView);
        toolBar = (Toolbar) toolbar.findViewById(R.id.id_tool_bar);
    }

    public void setTopTipsLayout(ViewGroup toptips){
        this.topTipsLayout = toptips;
    }

    public void setNoDataTipsLayout(ViewGroup noDataTipsLayout){
        this.noDataTipsLayout = noDataTipsLayout;
    }

    public FrameLayout getContentView() {
        return contentView;
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public ViewGroup getTopTipsView() {
        return topTipsLayout;
    }

    public ViewGroup getNoDataTipsView() {
        return noDataTipsLayout;
    }

    public View getUserView() {
        return userView;
    }
}
