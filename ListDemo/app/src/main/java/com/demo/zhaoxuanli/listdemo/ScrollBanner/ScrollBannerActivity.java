package com.demo.zhaoxuanli.listdemo.ScrollBanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.ScrollBanner.banner.BannerAdapter;
import com.demo.zhaoxuanli.listdemo.ScrollBanner.banner.BannerDto;
import com.demo.zhaoxuanli.listdemo.ScrollBanner.zhaoxuan.widget.ScrollBanner;

import java.util.ArrayList;
import java.util.List;

public class ScrollBannerActivity extends AppCompatActivity {

    private ScrollBanner scrollBanner;

    private Button btn1, btn2, btn3, btn4;

    private BannerAdapter bannerAdapter;

    private List<BannerDto> bannerDtos;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_banner);

        scrollBanner = (ScrollBanner) findViewById(R.id.scrollBanner);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        bannerDtos = new ArrayList<>();
        bannerDtos.add(new BannerDto(1, "#ff9100", 1, "1111111", 1, 5000));
        bannerDtos.add(new BannerDto(2, "#ff9100", 2, "2222222", 1, 3000));
        bannerDtos.add(new BannerDto(3, "#ff9100", 3, "3333333", 1, 4000));
        bannerDtos.add(new BannerDto(4, "#ff9100", 4, "4444444", 1, 3000));
        bannerDtos.add(new BannerDto(5, "#ff9100", 5, "5555555", 1, 5000));

        bannerAdapter = new BannerAdapter(this);
        bannerAdapter.setDatas(bannerDtos);

        scrollBanner.setAdapter(bannerAdapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollBanner.start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollBanner.stop();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollBanner.fixBanner(2);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollBanner.hideCustomBanner();
            }
        });


    }
}
