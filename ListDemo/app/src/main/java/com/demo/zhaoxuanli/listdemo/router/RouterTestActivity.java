package com.demo.zhaoxuanli.listdemo.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;

public class RouterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_test);


        Intent in = getIntent();
        String strExtra = in.getStringExtra("str");
        int intExtra = in.getIntExtra("int", -1);
        char charExtra = in.getCharExtra("char", 'a');

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(strExtra + " " + intExtra + " " + charExtra);

    }
}
