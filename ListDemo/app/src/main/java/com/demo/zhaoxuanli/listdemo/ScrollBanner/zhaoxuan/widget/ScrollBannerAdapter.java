package com.demo.zhaoxuanli.listdemo.ScrollBanner.zhaoxuan.widget;

import android.content.Context;
import android.view.View;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * Created by lizhaoxuan on 15/12/25.
 */
public abstract class ScrollBannerAdapter<T> {

    private int mBannerHeight;

    public ScrollBannerAdapter(Context context) {
        mBannerHeight = (int) context.getResources().getDimension(R.dimen.banner_width);
    }

    public abstract int getCount();

    public abstract Object getItem(int position);

    public abstract int getItemId(int position);

    public abstract View getView(int position, View convertView);

    public abstract View getFixView(int position, View convertView);

    public abstract int getItemViewType(int position);

    public abstract boolean isEmpty();

    public int getWheelTime(int position) {
        return 4000;
    }

    public int bannerHeight() {
        return mBannerHeight;
    }

    public abstract int setFocusable(int position);
}
