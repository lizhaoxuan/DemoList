package com.demo.zhaoxuanli.listdemo.pic_compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片压缩工具类
 * Created by zhaoxuan.li on 2015/9/21.
 */
public class BitmapCompress {

    /**
     * 将图片以等比例压缩。通常用于从文件读取图片
     * @param filename   图片路径名
     * @param sampleSize  压缩为1/sampleSize
     * @return
     */
    public static Bitmap doBitmapRatio(String filename,int sampleSize){
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filename,options);
    }

    /**
     * 生成缩略图，只适合较小的图片进行缩略图生成
     * @param bm
     * @param squareW
     * @param squareH
     * @return
     */
    public static Bitmap createThumbBitmap(Bitmap bm,int squareW,int squareH){
        boolean bool = false;
        if(squareH>50&&squareW>50)
            bool = true;
        return Bitmap.createScaledBitmap(bm, squareW, squareH, bool);
    }

    public static Bitmap doBitmapRatio(String filename,int srcWidth,int dstWidth){
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inScaled = true;
        options.inDensity = srcWidth;
        options.inTargetDensity = dstWidth;
        return BitmapFactory.decodeFile(filename,options);
    }





}
