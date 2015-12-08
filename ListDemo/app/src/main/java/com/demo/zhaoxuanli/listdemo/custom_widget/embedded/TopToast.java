package com.demo.zhaoxuanli.listdemo.custom_widget.embedded;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * Created by lizhaoxuan on 15/12/4.
 */
public class TopToast extends RelativeLayout {
    private TextView textView;

    public TopToast(Context context) {
        super(context);
        init(context, null);
    }

    public TopToast(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TopToast(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_top_toast, this, true);
        textView = (TextView) findViewById(R.id.tipsText);

        if (attrs == null) {
            return;
        }else{
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopToast);
            CharSequence keyStr = a.getText(R.styleable.TopToast_android_text);
            if (keyStr != null){
                textView.setText(keyStr);
            }

            a.recycle();
        }

    }

    public String getText(){
        return textView.getText().toString();
    }
    public void setText(String tip){
        textView.setText(tip);
    }
}
