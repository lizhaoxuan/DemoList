package com.demo.zhaoxuanli.listdemo.view_parameter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;

public class ViewParameterActivity extends AppCompatActivity {

    private RelativeLayout userLayout;
    private Button btn, btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        userLayout = (RelativeLayout) findViewById(R.id.userLayout);
        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "getX:" + userLayout.getX() + "  getY:" + userLayout.getY() +
                       "底部Y坐标:"+userLayout.getHeight();
                Toast.makeText(ViewParameterActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics metric = new DisplayMetrics();
                ViewParameterActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metric);
                String msg = "屏幕宽度:" + metric.widthPixels + " 屏幕高度:" + metric.heightPixels;     // 屏幕高度（像素）
                Toast.makeText(ViewParameterActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "getX:" + btn2.getX() + "  getY:" + btn2.getY() +
                        " 底部Y坐标:" + (btn2.getY() + btn2.getHeight());
                Toast.makeText(ViewParameterActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }


}
