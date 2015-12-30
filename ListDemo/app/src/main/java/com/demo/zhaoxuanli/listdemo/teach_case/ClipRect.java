package com.demo.zhaoxuanli.listdemo.teach_case;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lizhaoxuan on 15/12/29.
 */
public class ClipRect extends View{

    Context mContext;
    Paint mPaint;
    Path mPath;
    public ClipRect(Context context) {
        super(context);
        init();
    }
    public ClipRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ClipRect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPath = new Path();
    }
    protected void onDraw(Canvas canvas){
        Bitmap bitmap ;
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(bitmap);
        int width = 1000;
        int height =1000;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        //全屏绘制一个蓝色的矩形画面
        myCanvas.drawColor(0xF0888888);
        paint.setColor(0xFFFFFFFF);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        myCanvas.drawCircle(150,150,100,paint);

        canvas.drawBitmap(bitmap, 0, 0, null);

    }
    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 100, 100);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100, 100, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(30, 70, 30, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawText("Clipping", 100, 30, mPaint);
    }

}
