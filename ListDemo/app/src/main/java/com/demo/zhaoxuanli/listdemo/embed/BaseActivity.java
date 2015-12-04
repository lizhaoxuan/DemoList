package com.demo.zhaoxuanli.listdemo.embed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by lizhaoxuan on 15/12/4.
 */
public class BaseActivity extends AppCompatActivity {

    private PackageHelper packageHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        packageHelper = new PackageHelper(this,layoutResID) ;
        toolbar = packageHelper.getToolBar() ;

        setContentView(packageHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }

    public void onCreateCustomToolBar(android.support.v7.widget.Toolbar toolbar){
        toolbar.setContentInsetsRelative(0, 0);
    }

    protected void showTopTips(){
        packageHelper.getTopTipsLayout().setVisibility(View.VISIBLE);
    }
    protected void hideTopTips(){
        packageHelper.getTopTipsLayout().setVisibility(View.GONE);
    }

    protected void showNoDataTips(){
        packageHelper.getNoDataTipsLayout().setVisibility(View.VISIBLE);
    }

    protected void hideNoDataTips(){
        packageHelper.getNoDataTipsLayout().setVisibility(View.GONE);
    }


}
