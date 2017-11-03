package com.wnw.wnw.customviewtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wnw.wnw.customviewtest.R;

/**
 * @author wnw
 * @date 2017/11/2 0002 15:08
 */
public class CustomView02 extends View{

    private int mBorderColor;

    private int mBorderWidth;

    private float mSmallRadius;

    private float mLargeRadius;

    private float mSmallLength;

    private float mLargeLength;

    private Paint mPaint;

    private int mHour;

    private  int mMinute;

    public CustomView02(Context context) {
        this(context, null);
    }

    public CustomView02(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView02(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView02, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        Log.d("wnw", "count = " + count);
        for (int i = 0; i < count; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomView02_borderColor:
                    mBorderColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomView02_borderWidth:
                    //默认设置成16sp, TypeValue也可以把dp转化为px
                    mBorderWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
                    Log.d("wnw", "border width = " + mBorderWidth);
                    break;
                case R.styleable.CustomView02_smallLength:
                    mSmallLength = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView02_largeLength:
                    mLargeLength = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView02_smallRadius:
                    mSmallRadius = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView02_largeRadius:
                    mLargeRadius = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        //释放资源
        typedArray.recycle();

        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width ;
        int height;

        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = (int)(mLargeRadius * 2 + mLargeLength * 2);
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = (int)(mLargeRadius * 2 + mLargeLength * 2);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置空心效果
        mPaint.setStyle(Paint.Style.STROKE);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //画小圆和大圆
        canvas.drawCircle(getWidth()/2, getHeight()/2, mSmallRadius, mPaint);
        canvas.drawCircle(getWidth()/2, getHeight()/2, mLargeRadius, mPaint);

        //画60个齿轮,360 / 60 = 6; 每个齿轮是6度
        int perAngle = 6;
        int count = 60;

        for (int i = 0; i < count; i++){
            float startX;
            float startY;
            float endX;
            float endY;

            //角度转弧度 π/180 * 角度
            /**
             * 从12点开始，角度增加，顺时针旋转
             * */
            startX = getWidth() / 2 + mLargeRadius * (float) Math.sin(Math.PI / 180 * i * perAngle);
            startY = getHeight() / 2 - mLargeRadius * (float) Math.cos(Math.PI / 180 * i * perAngle);
            if (i % 5 == 0){
                endX = startX + mLargeLength * (float) Math.sin(Math.PI / 180 * i * perAngle);
                endY = startY - mLargeLength * (float) Math.cos(Math.PI / 180 * i * perAngle);
            }else {
                endX = startX + mSmallLength * (float) Math.sin(Math.PI / 180 * i * perAngle);
                endY = startY - mSmallLength * (float) Math.cos(Math.PI / 180 * i * perAngle);
            }
            //画每一段
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }

        //画分针
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                getWidth() / 2 + mLargeRadius * (float) Math.sin(Math.PI / 180 * 6 * mMinute),
                getHeight() / 2 - mLargeRadius * (float) Math.cos(Math.PI / 180 * 6 * mMinute),
                mPaint);

        //画时针
        // 小时占的角度：一个小时30度, %12是为了防止24小时制度
        float hourAngle = (mHour%12) * 30;
        //分钟占的角度：一个小时60分钟，一个小时占的角度是30度, 这里一定要把60强转成float
        float minuteAngle = mMinute / (float)60 * 30;
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float)(getWidth() / 2 + 0.7 * mLargeRadius * ((float) Math.sin(Math.PI / 180 * (hourAngle +minuteAngle )))),
                (float)(getHeight() / 2 - 0.7 * mLargeRadius * ((float) Math.cos(Math.PI / 180 * (hourAngle +minuteAngle )))),
                mPaint
        );
    }

    public void setTime(int hour, int minute){
        this.mHour = hour;
        this.mMinute = minute;
        //更新View
        invalidate();
    }
}
