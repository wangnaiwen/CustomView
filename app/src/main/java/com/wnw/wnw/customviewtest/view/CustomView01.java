package com.wnw.wnw.customviewtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wnw.wnw.customviewtest.R;

/**
 * @author wnw
 * @date 2017/11/2 0002 10:57
 *
 * 1. 自定义View的属性
 * 2. 在View的构造方法中获得我们自定义的属性
 * 3. 重写onMeasure，非必须
 * 4. 重写onDraw
 */
public class CustomView01 extends View{

    /**
     * 文字
     * */
    private String mTitleText;

    /**
     * 文字颜色
     * */
    private int mTitleTextColor;

    /**
     * 文字大小
     * */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本的绘制范围:一个矩形，这个矩形是刚刚好能够装下文字的大小
     * */
    private Rect mBound;

    /**
     * 画笔
     * */
    private Paint mPaint;

    public CustomView01(Context context) {
        this(context, null);
    }

    public CustomView01(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView01(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //取得我们的所有属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView01, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        //遍历这些属性，把我们的属性值取出来，并且赋值到相应的的对象上面
        for (int i = 0; i < n; i ++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomView01_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.CustomView01_titleTextColor:
                    //默认设置成黑色
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomView01_titleTextSize:
                    //默认设置成16sp, TypeValue也可以把sp转化为px
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }

        //程序在运行时维护了一个 TypedArray的池，程序调用时，会向该池中请求一个实例，用完之后，调用 recycle() 方法来释放该实例，从而使其可被其他模块复用
        typedArray.recycle();

        //初始化画笔等
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        //在这里添加点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * 如果调用了系统的onMeasure()方法，当view设置为：wrap_content或者是match_parent的时候，其最终都是：match_parent
     *
     * 1. 当view是指定准确大小的时候，就是其大小就是其指定的大小
     * 2. 当view不是指定准确大小（wrap_content或者match_parent），就要自己去测量
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        /**
         * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
         * UNSPECIFIED：表示子布局想要多大就多大，很少使用
         * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
         * */
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY){
            //说明这里是已经指定了准确的大小
            width = widthSize;

        }else{
            //测量文字所占的width
            //设置画笔的字体大小
            mPaint.setTextSize(mTitleTextSize);
            //获取得到的文字的边界，放入mBound这个矩形内，所以文字所占的width就是mBound的width
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            //view的width = 左边内边距 + 文字width + 右边内边距
            width = (int)(getPaddingLeft() + textWidth + getPaddingRight());

            Log.d("wnw", "width = " + getWidth());
        }

        if (heightMode == MeasureSpec.EXACTLY){
            //说明这里是已经指定了准确的大小
            height = heightSize;
        }else{
            //测量文字所占的height
            //这是画笔的字体大小
            mPaint.setTextSize(mTitleTextSize);
            //获取得到的文字的边界，放入mBound这个矩形内，所以文字所占的height就是mBound的height
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            //view的height = 上边内边距 + 文字height + 下边内边距
            height = (int)(getPaddingTop() + textHeight + getPaddingBottom());

            Log.d("wnw", " height = " + getHeight());
        }

        //设置宽和高
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //画背景，画一个矩形
        mPaint.setColor(Color.BLUE);
        //left, top, right, bottom
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //画文字
        mPaint.setColor(mTitleTextColor);

        //第三个参数，是baseline高度，是以baseline来画的，我们这里也就是文字底部这条线的高度
        canvas.drawText(mTitleText, getWidth()/2 - mBound.width()/2, getHeight()/2  + mBound.height()/2 , mPaint);
    }


}
