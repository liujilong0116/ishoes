package com.iai.ishoes.battery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.iai.ishoes.R;

/**
 * @author donkor
 * 自定义水平、垂直电池控件
 */
public class BatteryView extends View {
    private int mPower = 100;
    private int orientation;
    private int width;
    private int height;
    private int mColor;
    private int mState;
    private float mRadius = 4f;
    private int gap; // 电池内框距外框的margin

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        mColor = typedArray.getColor(R.styleable.Battery_batteryColor, 0xFF959595);
        orientation = typedArray.getInt(R.styleable.Battery_batteryOrientation, 0);
        mPower = typedArray.getInt(R.styleable.Battery_batteryPower, 100);
        gap = typedArray.getInteger(R.styleable.Battery_battery_view_inside_margin, 2);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对View上的內容进行测量后得到的View內容占据的宽度
        width = getMeasuredWidth();
        //对View上的內容进行测量后得到的View內容占据的高度
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断电池方向    horizontal: 0   vertical: 1
        if (orientation == 0) {
            drawHorizontalBattery(canvas);
        } else {
            drawVerticalBattery(canvas);
        }
    }

    /**
     * 绘制水平电池
     *
     * @param canvas
     */
    private void drawHorizontalBattery(Canvas canvas) {
        Paint paint = new Paint();
        Paint starpaint = new Paint();
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = width / 20.f;
        float strokeWidth_2 = strokeWidth / 2;

        paint.setStrokeWidth(strokeWidth);
        RectF r1 = new RectF(strokeWidth_2, strokeWidth_2, width - strokeWidth - strokeWidth_2, height - strokeWidth_2);
        //设置外边框颜色
        paint.setColor(Color.rgb(149, 149, 149));
        canvas.drawRoundRect(r1, mRadius, mRadius, paint);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        //画电池内矩形电量
        float offset = (width - strokeWidth * 2 - gap * 2) * mPower / 100.f;
        RectF r2 = new RectF(strokeWidth + gap, strokeWidth + gap, offset, height - strokeWidth - gap);
        //根据电池状态决定电池内矩形电量颜色
        if (mState == 1) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(r2, paint);
            starpaint.setColor(Color.rgb(133, 194, 38));//设置画笔颜色
            starpaint.setStrokeWidth(4f);//设置画笔宽度
            //画闪电
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lightning, null);
            canvas.drawBitmap(bitmap, width / 3f, height / 3f - 2, starpaint);
        }
        if (mState == 2) {
            paint.setColor(Color.rgb(149, 149, 149));
            canvas.drawRect(r2, paint);
        }
        if (mState == 3) {
            paint.setColor(Color.rgb(149, 149, 149));
            canvas.drawRect(r2, paint);

            //画闪电
            starpaint.setColor(Color.rgb(149, 149, 149));                    //设置画笔颜色
            starpaint.setStrokeWidth(4f);                       //设置画笔宽度

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lightning, null);
            canvas.drawBitmap(bitmap, width / 3f, height / 3f - 2, starpaint);
        }
        if (mState == 4) {
            paint.setColor(Color.RED);
            canvas.drawRect(r2, paint);
            //画闪电
            starpaint.setColor(Color.rgb(149, 149, 149));                    //设置画笔颜色
            starpaint.setStrokeWidth(4f);                       //设置画笔宽度

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lightning, null);
            canvas.drawBitmap(bitmap, width / 3f, height / 3f - 2, starpaint);
        }
        if (mState == 5) {
            paint.setColor(Color.RED);
            canvas.drawRect(r2, paint);
        }
        //画电池头  右减左，下减上
        RectF rectfHeader = new RectF(width - strokeWidth, height * 0.25f, width, height * 0.75f);
        //设置电池头颜色
        paint.setColor(Color.rgb(149, 149, 149));
        canvas.drawRect(rectfHeader, paint);
    }

    /**
     * 绘制垂直电池
     *
     * @param canvas
     */
    private void drawVerticalBattery(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = height / 20.0f;
        float strokeWidth2 = strokeWidth / 2;
        paint.setStrokeWidth(strokeWidth);
        int headHeight = (int) (strokeWidth + 0.5f);
        RectF rect = new RectF(strokeWidth2, headHeight + strokeWidth2, width - strokeWidth2, height - strokeWidth2);
        canvas.drawRect(rect, paint);
        paint.setStrokeWidth(0);
        float topOffset = (height - headHeight - strokeWidth) * (100 - mPower) / 100.0f;
        RectF rect2 = new RectF(strokeWidth, headHeight + strokeWidth + topOffset, width - strokeWidth, height - strokeWidth);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect2, paint);
        RectF headRect = new RectF(width / 4.0f, 0, width * 0.75f, headHeight);
        canvas.drawRect(headRect, paint);
    }

    /**
     * 设置电池电量
     *
     * @param power
     */
    public void setPower(int power) {
        this.mPower = power;
        if (mPower < 0) {
            mPower = 100;
        }
        invalidate();//刷新VIEW
    }

    /**
     * 设置电池颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.mColor = color;
        invalidate();
    }

    /**
     * 设置电池状态
     *
     * @return
     */
    public void setState(int state) {
        this.mState = state;
        invalidate();
    }
}