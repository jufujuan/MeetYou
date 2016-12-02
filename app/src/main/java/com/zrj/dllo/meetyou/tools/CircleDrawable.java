package com.zrj.dllo.meetyou.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * 用来画圆的drawable
 */
public class CircleDrawable extends Drawable {
    private Bitmap mBitmap;
    private Paint mPaint;
    private int r;//圆的半径

    public CircleDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        /**
         * 图像渲染BitmapShader
         * BitmapShader的作用是使用一张位图作为纹理来对某一区域进行填充。
         * 可以想象成在一块区域内铺瓷砖，只是这里的瓷砖是一张张位图而已。
         * 参数一:作为纹理填充的位图
         * 参数二:位图X方向上位图衔接形式
         * 参数三:位图Y方向上位图衔接形式
         * Shader.TileMode有3种参数可供选择，分别为CLAMP、REPEAT和MIRROR
         * CLAMP的作用是如果渲染器超出原始边界范围，则会复制边缘颜色对超出范围的区域进行着色。
         * REPEAT的作用是在横向和纵向上以平铺的形式重复渲染位图。
         * MIRROR的作用是在横向和纵向上以镜像的方式重复渲染位图。
         */
        BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        mPaint.setShader(shader);
        r = Math.min(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, r, mPaint);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /**
     * 关键操作:用来通知drawable的宽和高
     */
    @Override
    public int getIntrinsicHeight() {
        return 2 * r;
    }

    @Override
    public int getIntrinsicWidth() {
        return 2 * r;
    }
}
