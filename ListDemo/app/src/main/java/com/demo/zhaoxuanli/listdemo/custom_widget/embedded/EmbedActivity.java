package com.demo.zhaoxuanli.listdemo.custom_widget.embedded;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * 尝试封装 topTip,noDataTips,loading
 */
public class EmbedActivity extends BaseActivity {

    private Button button1,button2,button3,button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embed);

        initView();

    }

    private void initView(){
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopTips();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTopTips();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TopToast topToast = (TopToast)packageHelper.getTopTipsView();
//                topToast.setText("修改过的Tips提示");
                showNoDataTips();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNoDataTips();
            }
        });
    }
}
