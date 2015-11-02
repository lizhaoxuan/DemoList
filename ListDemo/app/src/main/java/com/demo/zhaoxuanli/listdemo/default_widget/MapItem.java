package com.demo.zhaoxuanli.listdemo.default_widget;

/**
 * Created by zhaoxuan.li on 2015/10/19.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;


/**
 * 公共的自定义控件 一个key和value的组合控件
 * 1：天气查询界面使用
 * Created by zhaoxuan.li on 2015/10/16.
 */
public class MapItem extends RelativeLayout {
    private TextView keyText,valueText;
    private OnItemClickListener listener;
    public MapItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapItem);
        String key =typedArray.getString(R.styleable.MapItem_keyText);
        String value = typedArray.getString(R.styleable.MapItem_valueText);
        LayoutInflater.from(context).inflate(R.layout.widget_map_item, this, true);
        keyText = (TextView)findViewById(R.id.keyText);
        valueText = (TextView)findViewById(R.id.valueText);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.widget_layout);
        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了…………");
                if(listener != null)
                    listener.onClickListener();
            }
        });

        if(key!=null)
            keyText.setText(key);
        if(value!=null)
            valueText.setText(value);

        typedArray.recycle();
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public String getKeyText() {
        return keyText.getText().toString();
    }
    public void setKeyText(String keyText) {
        this.keyText.setText(keyText);
    }
    public String getValueText() {
        return valueText.getText().toString();
    }
    public void setValueText(String valueText) {
        this.valueText .setText(valueText);
    }



    public interface OnItemClickListener{
        void onClickListener();
    }
}

