/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.demo.zhaoxuanli.listdemo.draw_music;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class LineRenderer extends Renderer
{
  private Paint mPaint;
  private Paint mFlashPaint;
  private boolean mCycleColor;
  private float amplitude = 0;


  /**
   * Renders the audio data onto a line. The line flashes on prominent beats
   * @param canvas
   * @param paint - Paint to draw lines with
   * @param paint - Paint to draw flash with
   */
  public LineRenderer(Paint paint, Paint flashPaint)
  {
    this(paint, flashPaint, false);
  }

  /**
   * Renders the audio data onto a line. The line flashes on prominent beats
   * @param canvas
   * @param paint - Paint to draw lines with
   * @param paint - Paint to draw flash with
   * @param cycleColor - If true the color will change on each frame
   */
  public LineRenderer(Paint paint,
                      Paint flashPaint,
                      boolean cycleColor)
  {
    super();
    mPaint = paint;
    mFlashPaint = flashPaint;
    mCycleColor = cycleColor;
  }

  @Override
  public void onRender(Canvas canvas, AudioData mBytes, Rect mRect)
  {
    if(mCycleColor)
    {
      cycleColor();
    }
    if (mBytes == null) {
        return;
    }
    if (mPoints == null || mPoints.length < mBytes.bytes.length * 4) {
    	//mPoints��Ҫ�����洢Ҫ��ֱ�ߵ�4�����꣨ÿ�����������꣬����һ��ֱ����Ҫ�����㣬Ҳ����4�����꣩
        mPoints = new float[mBytes.bytes.length * 4];
    }
    //mRect.set(0, 0, getWidth(), getHeight());
    //xOrdinate��x����̶ܿȣ���Ϊһ�λᴫ�����1024�����ݣ�ÿ��������Ҫ����һ��ֱ�ߣ�����x�����Ƿֳ�1023�Ρ���Ҫ�Ǿ���̫���ˣ�Ҳ��������һ������2�����Լ������ˡ�
    
    
    //int xOrdinate = (mBytes.bytes.length - 1)/127;
    int xOrdinate = (mBytes.bytes.length - 1)/5;

    //���µ�forѭ��������mBytes[i] mBytes[i+1] ����������ȥ����4������ֵ���Ӷ��ڿ̻����������꣬��������
    for (int i = 0; i <xOrdinate ; i++) {

    	//��i�������ܺ����ϵ����꣬
        mPoints[i * 4] = mRect.width() * i / xOrdinate;

        //��i��������������ϵ����ꡣ���ڻ��������������1/2Ϊ��׼�ߣ�mRect.height() / 2�������еĵ�������Դ���Ϊ������ǡ�
        //((byte) (mBytes[i] + 128))���һֱû����⣬���+128��Ϊ�˽�����ȫ������Ϊ����������ôǿתΪbyte���ֱ��-127��128��ô����Ҫ��˭֪��ԭ��������Ը�����.....
        //(mRect.height() / 2) / 128���ǽ�����֮һ���ܳ��Ȼ����128���̶ȣ���Ϊ���ǵ�������byte���ͣ����Կ̻���128���̶�����
        mPoints[i * 4 + 1] = mRect.height() / 2+ ((byte) (mBytes.bytes[i] + 128)) * (mRect.height() / 2) / 128;


        //���¾��ǿ̻���i+1�������ˣ�ԭ��Ϳ̻���i��һ��
        mPoints[i * 4 + 2] = mRect.width() * (i + 1) / xOrdinate;
        mPoints[i * 4 + 3] = mRect.height() / 2 + ((byte) (mBytes.bytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
    }

    //ѭ�������󣬾͵õ�����һ�β��ε����п̻����ֱ꣬�ӻ��ڻ����Ͼͺ���
    canvas.drawLines(mPoints, mFlashPaint);
}
    
    
    
    
    
    
    
   
  

  @Override
  public void onRender(Canvas canvas, FFTData data, Rect rect)
  {
    // Do nothing, we only display audio data
  }

  private float colorCounter = 0;
  private void cycleColor()
  {
    int r = (int)Math.floor(128*(Math.sin(colorCounter) + 3));
    int g = (int)Math.floor(128*(Math.sin(colorCounter + 1) + 1));
    int b = (int)Math.floor(128*(Math.sin(colorCounter + 7) + 1));
    mPaint.setColor(Color.argb(128, r, g, b));
    colorCounter += 0.03;
  }
}
