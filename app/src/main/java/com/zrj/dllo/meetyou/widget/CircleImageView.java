package com.zrj.dllo.meetyou.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zrj.dllo.meetyou.tools.DensityUtil;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public class CircleImageView extends ImageView {

    private Bitmap bgBitmap;//大的背景的图片

    private int mWidth, mHeight;//该view的宽高


    private final static int MODE_NORMAL = 0;//默认状态
    private final static int MODE_SWEEP = 1;//点击重新搜索扫描
    private int mMode;
    private Canvas canvas;

    private Context context;
    private int bitmapWidth = 150;//传入进来的图片(正方形)的宽(单位dp)默认150dp
   // private int bitmapHeight = 150;//传入进来的图片的高(单位dp)默认150dp

    /*********** 构造方法*************/
    /**
     * 构造方法
     *
     * @param context
     */
    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /************ 初始化****************/
    /**
     * 初始化数据
     */
    private void init() {
        //设置初始的状态
        mMode = MODE_NORMAL;
    }

    /************** 绘制到界面上*****************/
    /**
     * 绘图方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        mHeight = getHeight();
        mWidth = getWidth();
        switch (mMode) {
            case MODE_NORMAL:
                drawCircleImg(canvas, mWidth/2, bgBitmap);
                drawCircleBoder(canvas, Color.WHITE, 5, mWidth/2);
                break;
        }
    }
    /***************各种画图*******************/
    /**
     * 绘制圆形图片
     *
     * @param canvas 画布
     * @param r      圆的半径
     * @param bitmap 需要画圆的图片
     */
    private void drawCircleImg(Canvas canvas, float r, Bitmap bitmap) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawCircle(mWidth / 2, mHeight / 2, r, mPaint);
    }

    /**
     * 绘制圆形的边框
     *
     * @param canvas      画布
     * @param color       边框颜色
     * @param strokeWidth 边框的宽度
     * @param r           绘制的半径
     */
    private void drawCircleBoder(Canvas canvas, int color, float strokeWidth, float r) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(mWidth / 2, mHeight / 2, r, mPaint);
    }


    /****************对外的方法**********************/
    /**
     * 设置背景图片
     *
     * @param bm
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        Bitmap bitmap=Bitmap.createScaledBitmap(bm, DensityUtil.dip2Pix(context, bitmapWidth), DensityUtil.dip2Pix(context, bitmapWidth), true);
        //将图片进行缩放
        this.bgBitmap = bitmap;
        invalidate();
    }

}


