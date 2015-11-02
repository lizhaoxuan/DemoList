package com.demo.zhaoxuanli.listdemo.popup_tips;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * Created by zhaoxuan.li on 2015/10/13.
 */
public class CustomToast {

    public void showToast(Activity activity){

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.widget_top_toast,
                (ViewGroup) activity.findViewById(R.id.popLayout));

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }
}
