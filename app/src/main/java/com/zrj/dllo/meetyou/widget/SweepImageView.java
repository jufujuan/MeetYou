package com.zrj.dllo.meetyou.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.zrj.dllo.meetyou.tools.DensityUtil;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public class SweepImageView extends ImageView {

    private Bitmap bgBitmap;//大的背景的图片
    private int mWidth, mHeight;//该view的宽高
    private float angle;//角度

    private final static int MODE_NORMAL = 0;//默认状态
    private final static int MODE_SWEEP = 1;//点击重新搜索扫描
    private int mMode;

    private Context context;
    private int bitmapWidth = 300;//传入进来的图片的宽(单位dp)默认300dp
    private int bitmapHeight = 300;//传入进来的图片的高(单位dp)默认300dp
    private long sweepDuration = 5000;//动画一次执行的时间(默认5s)
    private int sweepColor = Color.RED;//扫描的颜色
    private int sweepWidth = 5;//扫描的边缘线的宽度
    private int sweepAlpha = 100;//扫描线的透明度
    private float sweepR;

    /*********** 构造方法*************/
    /**
     * 构造方法
     *
     * @param context
     */
    public SweepImageView(Context context) {
        this(context, null);
    }

    public SweepImageView(Context context, AttributeSet attrs) {
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
        addSweepAnim(sweepDuration, new LinearInterpolator());
        sweepR = 0;
    }

    /***************
     * 提供给属性动画的方法
     ******************/

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSweepR() {
        return sweepR;
    }

    public void setSweepR(float sweepR) {
        this.sweepR = sweepR;
    }

    public int getSweepAlpha() {
        return sweepAlpha;
    }

    public void setSweepAlpha(int sweepAlpha) {
        this.sweepAlpha = sweepAlpha;
    }

    /************** 绘制到界面上*****************/
    /**
     * 绘图方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mHeight = getHeight();
        mWidth = getWidth();

        drawCircleImg(canvas, mWidth / 2, bgBitmap);
        switch (mMode) {
            case MODE_NORMAL:
                canvas.save();
                drawArc(canvas, sweepColor, 80, new int[]{Color.WHITE, sweepColor}, sweepWidth);
                drawCircleSweep(canvas, sweepR-3, sweepWidth, sweepAlpha, sweepColor);
                canvas.restore();
                break;
        }
    }
    /***************各种画图*******************/
    /**
     * 绘制圆形图片(背景和头像)
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
     * 绘制扫面圆圈(点击后实现)
     *
     * @param canvas 画布
     * @param r      圆的半径
     * @param width  需要画圆的宽度
     */
    private void drawCircleSweep(Canvas canvas, float r, int width, int alpha, int color) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);
        mPaint.setAlpha(alpha);
        mPaint.setColor(color);
        canvas.drawCircle(mWidth / 2, mHeight / 2, r, mPaint);
    }

    /**
     * 绘制扇形
     *
     * @param canvas 画布
     * @param color  扇形半径
     * @param alpha  扇形的透明度
     * @param colors 扇形渐变色的转变
     * @param width  边缘线的颜色
     */
    private void drawArc(Canvas canvas, int color, int alpha, int[] colors, int width) {
        //关键代码(画布是可以转的)
        canvas.rotate(angle, mWidth / 2, mHeight / 2);
        Paint mPaint = new Paint();
        RectF arcRectF = new RectF(0, 0, mWidth, mHeight);
        //关键代码,实现渐变色
        SweepGradient gradient = new SweepGradient(mWidth / 2, mHeight / 2, colors, null);
        mPaint.setColor(color);
        mPaint.setAlpha(alpha);
        mPaint.setShader(gradient);
        canvas.drawArc(arcRectF, 0, 360, false, mPaint);
        //绘制线
        Paint lPaint = new Paint();
        lPaint.setColor(color);
        lPaint.setAntiAlias(true);
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeWidth(width);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth, mHeight / 2, lPaint);
        //绘制外边缘线
        Paint bigPaint = new Paint();
        bigPaint.setAntiAlias(true);
        bigPaint.setStyle(Paint.Style.STROKE);
        bigPaint.setStrokeWidth(width);
        bigPaint.setColor(color);
        SweepGradient bigShader = new SweepGradient(mWidth / 2, mHeight / 2, colors, null);
        bigPaint.setShader(bigShader);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2-3, bigPaint);
    }

    /**
     * 添加扫描动画
     *
     * @param duration     动画时间
     * @param interpolater 动画的插值器
     */
    private void addSweepAnim(long duration, final TimeInterpolator interpolater) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "angle", 0, 360f);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(interpolater);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        objectAnimator.start();
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
        Bitmap u = Bitmap.createScaledBitmap(bm, DensityUtil.dip2Pix(context, bitmapWidth), DensityUtil.dip2Pix(context, bitmapHeight), true);
        this.bgBitmap = u;
        invalidate();
    }

    public void addSweepRestartAnim() {
        ObjectAnimator rAnim = ObjectAnimator.ofFloat(this,
                "sweepR",
                DensityUtil.dip2Pix(context, 0.7f * bitmapWidth / 4),
                DensityUtil.dip2Pix(context, bitmapWidth / 2));
        rAnim.setDuration(6000);
        rAnim.setInterpolator(new LinearInterpolator());
        rAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        rAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                sweepR = 0;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        rAnim.start();
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }

    public void setBitmapHeight(int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }

    public void setSweepDuration(long sweepDuration) {
        this.sweepDuration = sweepDuration;
    }

    public void setSweepColor(int sweepColor) {
        this.sweepColor = sweepColor;
    }

    public void setSweepWidth(int sweepWidth) {
        this.sweepWidth = sweepWidth;
    }
}
