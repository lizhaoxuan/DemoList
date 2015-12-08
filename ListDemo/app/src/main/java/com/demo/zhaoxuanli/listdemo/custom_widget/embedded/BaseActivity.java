package com.demo.zhaoxuanli.listdemo.custom_widget.embedded;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 *
 * Created by lizhaoxuan on 15/12/4.
 */
public class BaseActivity extends AppCompatActivity {

    protected PackageHelper packageHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);


        //初始化Package
        initPackageHelper(layoutResID);

        toolbar = packageHelper.getToolBar() ;

        setContentView(packageHelper.getRootView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }

    /**
     * 可以通过重写该方法，改变BaseActivity创建模式
     * @param layoutResID
     */
    protected void initPackageHelper(int layoutResID){
        packageHelper = new PackageHelper.Builder(this,layoutResID)
                //.setToolbar(R.layout.widget_toolbar,R.id.id_tool_bar)
                .setTopWidget(new TopToast(this))
                .setCoverWidgetArray(new View[]{new NoDataTips(this)})
                .build();
    }

    public void onCreateCustomToolBar(android.support.v7.widget.Toolbar toolbar){
        toolbar.setContentInsetsRelative(0, 0);
    }

    protected void showTopTips(){
        packageHelper.showTopWidget();

//        Animation animation = new TranslateAnimation(0,0,0,height);
//        animation.setDuration(500);
//        Animation useranimation = new TranslateAnimation(0,0,0,height);
//        useranimation.setDuration(500);

//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(animation);

//        userView.startAnimation(useranimation);
//        tipsView.startAnimation(animation);
//        animation.setFillBefore(false);
//        useranimation.setFillBefore(true);

//        animation.setFillAfter(true);
//        useranimation.setFillAfter(true);
//        Toast.makeText(this,(""+(tipsView.getVisibility()==View.GONE)),Toast.LENGTH_SHORT).show();
//        useranimation.setFillBefore(false);
//        Animation userAnimation = AnimationUtils.loadAnimation(this, R.anim.out_top_to_down_1);
//        userView.startAnimation(userAnimation);
//        Animation tipsAnimation = AnimationUtils.loadAnimation(this, R.anim.out_top_to_down_1);
//        tipsView.startAnimation(tipsAnimation);
//        tipsAnimation.setFillBefore(false);

    }
    protected void hideTopTips(){
        packageHelper.hideTopWidget();
    }

    protected void showNoDataTips(){
        packageHelper.getViewForCoverWidget(0).setVisibility(View.VISIBLE);
    }

    protected void hideNoDataTips(){
        packageHelper.getViewForCoverWidget(0).setVisibility(View.GONE);
    }


}
