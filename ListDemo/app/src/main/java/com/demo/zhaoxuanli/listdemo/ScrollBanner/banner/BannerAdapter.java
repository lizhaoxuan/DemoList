package com.demo.zhaoxuanli.listdemo.ScrollBanner.banner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.ScrollBanner.zhaoxuan.widget.ScrollBannerAdapter;

import java.util.List;

/**
 * Created by lizhaoxuan on 15/12/25.
 */
public class BannerAdapter extends ScrollBannerAdapter<BannerDto> {

    private List<BannerDto> mDatas;
    private LayoutInflater mInflater;

    public BannerAdapter(Context context) {
        super(context);
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemId(int position) {
        return mDatas.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView) {
        BannerDto bannerDto = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.widget_banner, null);
            viewHolder.removeText = (ImageView) convertView.findViewById(R.id.removeText);
            viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleText);
            viewHolder.enterText = (TextView) convertView.findViewById(R.id.enterText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundColor(Color.parseColor(bannerDto.getBgColor()));
        viewHolder.titleText.setText(bannerDto.getTitle());
        return convertView;
    }

    @Override
    public View getFixView(int position, View convertView) {
        BannerDto bannerDto = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.widget_banner, null);
            viewHolder.removeText = (ImageView) convertView.findViewById(R.id.removeText);
            viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleText);
            viewHolder.enterText = (TextView) convertView.findViewById(R.id.enterText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleText.setText(bannerDto.getTitle());
        return convertView;
    }

    @Override
    public int setFocusable(int position) {
        return 0;
    }

    @Override
    public int getWheelTime(int position) {
        return mDatas.get(position).getWheelTime();
    }

    @Override
    public boolean isEmpty() {
        return mDatas.isEmpty();
    }

    private static class ViewHolder {
        ImageView removeText;
        TextView titleText;
        TextView enterText;

    }

}

