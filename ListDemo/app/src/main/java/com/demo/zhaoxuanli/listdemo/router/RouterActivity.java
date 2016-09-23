package com.demo.zhaoxuanli.listdemo.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class RouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);


        String str = "eleme://a=me.ele.crowdsource.view&login.LoginActivity";
        Log.d("TAG", "index:" + str.indexOf("://"));
        Log.d("TAG", "aaa:" + str.substring(0, 5));
        Log.d("TAG", "bbb:" + str.substring(8));

        String str2 = "me.ele.crowdsource.view.login.LoginActivity&ios.LoginController";
        String[] strArray = str2.split("&");
        Log.d("TAG", strArray[0] + "  " + strArray[1]);

        String urlEncoder = "";
        String urlDecoder = "";
        try {
            urlEncoder = URLEncoder.encode(str, "UTF-8");
            Log.d("TAG", urlEncoder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            urlDecoder = URLDecoder.decode(urlEncoder, "UTF-8");
            Log.d("TAG", urlDecoder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
