package com.demo.zhaoxuanli.listdemo.default_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * describe
 * zhaoxuan.li
 * 2015/10/28.
 */
public class ProgressBarItem extends LinearLayout {

    private TextView mNameText;
    private ProgressBar mProgressBar;

    public ProgressBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarItem);
        String key =typedArray.getString(R.styleable.ProgressBarItem_barName);

        LayoutInflater.from(context).inflate(R.layout.widget_progress_bar_item, this, true);
        mNameText = (TextView)findViewById(R.id.nameText);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        if(key!=null)
            mNameText.setText(key);
    }

    /**
     * 设置进度条的值
     * @param n
     */
    public void setProgressBar(int n){
        mProgressBar.setProgress(n);
    }
    public int getProgressBar(){
        return mProgressBar.getProgress();
    }
    public void setNameText(String key){
        mNameText.setText(key);
    }
    public String getNameText(){
        return mNameText.getText().toString();
    }

}
