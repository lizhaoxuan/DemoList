package com.demo.zhaoxuanli.listdemo.teach_case;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * Created by lizhaoxuan on 15/12/29.
 */
public class PointView extends View {

    private int[] mLocation = new int[2];
    private int mHeight;
    private int mWidth;
    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;

    public PointView(Context context, View target) {
        super(context);
        mPaint = new Paint();
        target.getLocationOnScreen(mLocation);
        mHeight = target.getHeight();
        mWidth = target.getWidth();

        // 获得屏幕的width
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }

    public PointView(Context context, int x, int y, int width, int height) {
        super(context);
        mLocation[0] = x;
        mLocation[1] = y;
        mHeight = height;
        mWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipRect(0,0,mScreenWidth, mScreenHeight);
        canvas.clipRect(mLocation[0],mLocation[1],mWidth,mHeight, Region.Op.XOR);
        mPaint.setAlpha(60);
        mPaint.setColor(getResources().getColor(R.color.gray_tag_1));
        //canvas.drawOval();
    }

}
