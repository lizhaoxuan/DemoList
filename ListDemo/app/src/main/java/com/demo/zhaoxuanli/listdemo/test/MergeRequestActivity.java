package com.demo.zhaoxuanli.listdemo.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;

public class MergeRequestActivity extends AppCompatActivity {

    private MergeRequestMap mergeRequestMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merage_request);

        mergeRequestMap = new MergeRequestMap();

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getOne(One one) {
        showToast(one.name);
    }

    private void getTwo(Two two) {
        showToast(two.name);
    }

    private void getThree(Three three) {
        showToast(three.name);
    }

    private void getFour(Four four) {
        showToast(four.name);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
