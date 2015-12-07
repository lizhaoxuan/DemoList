package com.demo.zhaoxuanli.listdemo.embed;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
 * 层次结构
 * FrameLayout（rootView）{   //根布局
 *      ToolBar
 *      LinearLayout(rootView){   //内容布局
 *          TopWidget     //顶部嵌入式通知消息
 *          UserView    //真正用户布局内容
 *          BottomWidget   //底部嵌入View
 *
 *      }
 *      NoDataTips(match,match,gone)   //空数据提示控件
 *      ErrorTips(match,match,gone)    //错误提示控件
 *      ...
 *      Loading(match,match,gone)      //loading
 * }
 *
 * Tips:
 * 纯静态页面可直接传入布局id，其他建议嵌入控件采用自定义组件
 * 接收参数 int or View
 * NoDataTip,ErrorTip建议使用一个自定义组件集成
 * Created by lizhaoxuan on 15/12/4.
 */
public class PackageHelper {

    /*上下文，创建view的时候需要用到*/
    private Context context;

    /*root view*/
    private FrameLayout rootView;

    /*content LinearLayout*/
    private LinearLayout contentLayout;

    /*用户定义的view*/
    private View userView;

    /*toolbar*/
    private Toolbar toolbar;

    /*顶部控件*/
    private View topWidget;

    /*覆盖式控件列表*/
    private View [] coverWidgetArray;

    /*底部控件列表*/
    private View [] bottomWidgetArray;


    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    private PackageHelper(Context context ,View userView,Toolbar toolbar,View topWidget,
                         View [] bottomWidgetArray,View [] coverWidgetArray) {
        this.context = context;
        this.userView = userView;
        this.toolbar = toolbar;
        this.topWidget = topWidget;
        this.bottomWidgetArray = bottomWidgetArray;
        this.coverWidgetArray = coverWidgetArray;

        //初始化根布局
        initRootView();

        //添加内容布局
        initContentLayout();

        addTopWidget();
        addUserView();
        addBottomWidget();
        addCoverWidget();
    }

    private void initRootView() {
        //直接创建一个帧布局，作为视图容器的父容器
        rootView = new FrameLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);
    }

    private void initContentLayout() {
        contentLayout = new LinearLayout(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(toolbar != null){
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(ATTRS);
            //获取主题中定义的悬浮标志
            boolean overly = typedArray.getBoolean(0, false);
            //获取主题中定义的toolbar的高度
            int toolBarSize = (int) typedArray.getDimension(1, (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
            typedArray.recycle();
            //如果是悬浮状态，则不需要设置间距
            params.topMargin = overly ? 0 : toolBarSize;
        }

        contentLayout.setLayoutParams(params);
        contentLayout.setOrientation(LinearLayout.VERTICAL);

        rootView.addView(contentLayout);
    }


    private void addTopWidget(){
        if(topWidget != null){
            LinearLayout.LayoutParams topTipsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
            topTipsParams.topMargin = - (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
            contentLayout.addView(topWidget, topTipsParams);
        }
    }

    private void addUserView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayout.addView(userView, params);
    }

    private void addBottomWidget(){
        if(bottomWidgetArray != null){
            for (View view : bottomWidgetArray){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                contentLayout.addView(view, params);
                view.setVisibility(View.GONE);
            }
        }
    }

    private void addCoverWidget(){
        if(coverWidgetArray != null){
            for (View view : coverWidgetArray){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                rootView.addView(view, params);
                view.setVisibility(View.GONE);
            }
        }
    }


    /*--------------- 各View get方法 ----------------*/
    public FrameLayout getRootView() {
        return rootView;
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public View getTopWidget() {
        return topWidget;
    }

    public View getUserView() {
        return userView;
    }

    public View getViewForCoverWidget(int position){
        try{
            return coverWidgetArray[position];
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

    }

    public View [] getCoverWidgetArray(){
        return coverWidgetArray;
    }

    public View getViewForBottomWidget(int position){
        try{
            return coverWidgetArray[position];
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }

    public View [] getBottomWidgetArray(){
        return bottomWidgetArray;
    }


    /**
     * TopWidget的显示和隐藏
     * 因为TopWidget并不像bottomWidget 和 coverWidget 一样只是gone和visible
     * 考虑用户体验，TopWidget通过向上偏移实现隐藏，所以其显示隐藏交由PackageHelper负责
     */
    public void showTopWidget(){
        if(topWidget == null){
            return ;
        }
        int height = topWidget.getHeight();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(topWidget,
                "y",  -height ,  0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(userView,
                "y",  0f ,  height);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        //animSet.play(anim2).with(anim1);
        animSet.setDuration(1000);
        animSet.start();

    }

    public void hideTopWidget(){
        if(topWidget == null){
            return ;
        }
        int height = topWidget.getHeight();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(topWidget,
                "y",  0f ,  -height);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(userView,
                "y",  height ,  0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        //animSet.play(anim2).with(anim1);
        animSet.setDuration(1000);
        animSet.start();
    }

    public static class Builder{
        private Context context;
        /*视图构造器*/
        private LayoutInflater inflater;
        /*用户定义的view*/
        private View userView;
        /*toolbar*/
        private Toolbar toolbar;
        /*顶部控件*/
        private View topWidget;
        /*覆盖式控件列表*/
        private View [] coverWidgetArray;
        /*底部控件列表*/
        private View [] bottomWidgetArray;
        /*haveToolBar*/
        private boolean haveToolBar = false;
        public Builder(Context context,int layoutId){
            this.context = context;
            inflater = LayoutInflater.from(context);
            userView = inflater.inflate(layoutId, null);
        }

        public Builder setToolbar(int layoutId,int viewId){
            View view = inflater.inflate(layoutId,null);
            toolbar = (Toolbar) view.findViewById(viewId);
            return this;
        }

        public Builder setTopWidget(int layoutResID){
            topWidget = inflater.inflate(layoutResID,null);
            return this;
        }

        public Builder setTopWidget(View view){
            topWidget = view;
            return this;
        }

        public Builder setBottomWidgetArray(int [] viewIds){
            int length = viewIds.length;
            bottomWidgetArray = new View[length];
            for (int i = 0; i < length ; i++) {
                bottomWidgetArray[i] = inflater.inflate(viewIds[i],null);
            }
            return this;
        }

        public Builder setBottomWidgetArray(View [] views){
            bottomWidgetArray = views;
            return this;
        }

        public Builder setCoverWidgetArray(int [] viewIds){
            int length = viewIds.length;
            coverWidgetArray = new View[length];
            for (int i = 0; i < length ; i++) {
                coverWidgetArray[i] = inflater.inflate(viewIds[i],null);
            }
            return this;
        }

        public Builder setCoverWidgetArray(View [] views){
            coverWidgetArray = views;
            return this;
        }
        public PackageHelper build(){
            return new PackageHelper(context,userView,toolbar,topWidget,
                    bottomWidgetArray,coverWidgetArray);
        }
    }

}
