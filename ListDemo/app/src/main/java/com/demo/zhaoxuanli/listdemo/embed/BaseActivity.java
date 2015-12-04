package com.demo.zhaoxuanli.listdemo.embed;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;

/**
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
        final View tipsView = packageHelper.getTopTipsView();
        final View userView = packageHelper.getUserView();

        int height = 50;

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
        //Toast.makeText(this,(""+(tipsView.getVisibility()==View.GONE)),Toast.LENGTH_SHORT).show();
        //useranimation.setFillBefore(false);
//        Animation userAnimation = AnimationUtils.loadAnimation(this, R.anim.out_top_to_down_1);
//        userView.startAnimation(userAnimation);
//        Animation tipsAnimation = AnimationUtils.loadAnimation(this, R.anim.out_top_to_down_1);
//        tipsView.startAnimation(tipsAnimation);
        //tipsAnimation.setFillBefore(false);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(tipsView,
                "y",  -50f ,  0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(userView,
                "y",  0f ,  50.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        //animSet.play(anim2).with(anim1);
        animSet.setDuration(1000);
        animSet.start();

    }
    protected void hideTopTips(){
        final View tipsView = packageHelper.getTopTipsView();
        final View userView = packageHelper.getUserView();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(tipsView,
                "y",  0f ,  -50f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(userView,
                "y",  50f ,  0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        //animSet.play(anim2).with(anim1);
        animSet.setDuration(1000);
        animSet.start();
    }

    protected void showNoDataTips(){
        packageHelper.getNoDataTipsView().setVisibility(View.VISIBLE);
    }

    protected void hideNoDataTips(){
        packageHelper.getNoDataTipsView().setVisibility(View.GONE);
    }


}
