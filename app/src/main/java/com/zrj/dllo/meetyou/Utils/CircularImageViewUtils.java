package com.zrj.dllo.meetyou.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by ${ZhaoXuancheng} on 16/11/21.
 * 画圆工具类
 */

public class CircularImageViewUtils extends ImageView

{

    private int borderWidth = 4;

    private int viewWidth;

    private int viewHeight;

    private Bitmap image;

    private Paint paint;

    private Paint paintBorder;

    private BitmapShader shader;

    public CircularImageViewUtils(Context context)

    {

        super(context);

        setup();

    }

    public CircularImageViewUtils(Context context, AttributeSet attrs)

    {

        super(context, attrs);

        setup();

    }

    public CircularImageViewUtils(Context context, AttributeSet attrs, int defStyle)

    {

        super(context, attrs, defStyle);

        setup();

    }

    private void setup()

    {

// init paint

        paint = new Paint();

        paint.setAntiAlias(true);

        paintBorder = new Paint();

        setBorderColor(Color.WHITE);

        paintBorder.setAntiAlias(true);

        this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);

        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);

    }

    public void setBorderWidth(int borderWidth) {

        this.borderWidth = borderWidth;

        this.invalidate();

    }

    public void setBorderColor(int borderColor)

    {

        if (paintBorder != null)

            paintBorder.setColor(borderColor);

        this.invalidate();

    }

    private void loadBitmap()

    {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

        if (bitmapDrawable != null)

            image = bitmapDrawable.getBitmap();

    }

    @SuppressLint("DrawAllocation")

    @Override

    public void onDraw(Canvas canvas)

    {

// load the bitmap

        loadBitmap();

// init shader

        if (image != null)

        {

            shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvas.getWidth(), canvas.getHeight(), false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            paint.setShader(shader);

            int circleCenter = viewWidth / 2;

// circleCenter is the x or y of the view's center

// radius is the radius in pixels of the cirle to be drawn

// paint contains the shader that will texture the shape

            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);

            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint);

        }

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)

    {

        int width = measureWidth(widthMeasureSpec);

        int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

        viewWidth = width - (borderWidth * 2);

        viewHeight = height - (borderWidth * 2);

        setMeasuredDimension(width, height);

    }

    private int measureWidth(int measureSpec)

    {

        int result = 0;

        int specMode = MeasureSpec.getMode(measureSpec);

        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY)

        {

// We were told how big to be

            result = specSize;

        } else

        {

// Measure the text

            result = viewWidth;

        }

        return result;

    }

    private int measureHeight(int measureSpecHeight, int measureSpecWidth)

    {

        int result = 0;

        int specMode = MeasureSpec.getMode(measureSpecHeight);

        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY)

        {

// We were told how big to be

            result = specSize;

        } else

        {

// Measure the text (beware: ascent is a negative number)

            result = viewHeight;

        }

        return (result + 2);

    }


    /**
     * 把bitmap转成圆形
     */

    public Bitmap toRoundBitmap(Bitmap bitmap) {

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        int r = 0;

//取最短边做边长

        if (width < height) {

            r = width;

        } else {

            r = height;

        }

//构建一个bitmap

        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

//new一个Canvas，在backgroundBmp上画图

        Canvas canvas = new Canvas(backgroundBm);

        Paint p = new Paint();

//设置边缘光滑，去掉锯齿

        p.setAntiAlias(true);

        RectF rect = new RectF(0, 0, r, r);

//通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，

//且都等于r/2时，画出来的圆角矩形就是圆

        canvas.drawRoundRect(rect, r / 2, r / 2, p);

//设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//canvas将bitmap画在backgroundBmp上

        canvas.drawBitmap(bitmap, null, rect, p);

        return backgroundBm;

    }

}