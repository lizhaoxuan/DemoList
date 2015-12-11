package com.demo.zhaoxuanli.listdemo.combo_widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * 嵌入式控件 演示Activity
 */
public class EmbedActivity extends BaseComboActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embed);

        super.setTitle("哈哈");

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.button1:  embedUiManager.showTopWidget();
                break;
            case R.id.button2:  embedUiManager.hideTopWidget();
                break;
            case R.id.button3:  showNoDataTips();
                break;
            case R.id.button4:  hideNoDataTips();
                break;
            case R.id.button5:  showLoading();
                break;
            case R.id.button6:  hideLoading();
                break;
        }

    }
}
