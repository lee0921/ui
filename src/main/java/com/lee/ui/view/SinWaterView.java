package com.lee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lee.ui.utils.ScreenUtils;

/**
 * 正选水波纹
 *
 * y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
 */

public class SinWaterView extends View {
    private Paint mPaint;

    private static final int STRETCH_FACTOR_A = 50;
    private static final int OFFSET_Y = 0;
    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 6;
    // 第二条水波移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 3;


    private int mHeight;
    private int mWidth;
    private float[] mYPositions;//波纹的原始值
    private float[] mResetOneYPositions;//第一条水波的值
    private float[] mResetTwoYPositions;//第二条水波的值
    private float mRepeatTime;//每次重复的时间

    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;
    private int mCurrentY = 400;


    public SinWaterView(Context context) {
        this(context, null);
    }

    public SinWaterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinWaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);


        mXOffsetSpeedOne = ScreenUtils.dpToPxInt(context, TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = ScreenUtils.dpToPxInt(context, TRANSLATE_X_SPEED_TWO);

        mWidth = ScreenUtils.getScreenWidth(context);
        mHeight = ScreenUtils.getScreenHeight(context);

        mYPositions = new float[mWidth];
        mResetOneYPositions = new float[mWidth];
        mResetTwoYPositions = new float[mWidth];

        mRepeatTime = (float) (2 * Math.PI / mWidth);

        for (int i = 0; i < mWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mRepeatTime * i) + OFFSET_Y);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetPositonY();
        for (int i = 0; i < mWidth; i++) {

            // 竖直方向画直线
            // 绘制第一条水波纹
            canvas.drawLine(i, mHeight - mResetOneYPositions[i] - mCurrentY, i, mHeight, mPaint);

            // 绘制第二条水波纹
            canvas.drawLine(i, mHeight - mResetTwoYPositions[i] - mCurrentY, i, mHeight, mPaint);

        }
        mCurrentY += 2;
        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        if (mXOneOffset >= mWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mWidth) {
            mXTwoOffset = 0;
        }

        if (mCurrentY == mHeight - 100){
            mCurrentY = 400;
        }

        postInvalidate();


    }


    private void resetPositonY() {
        int yOneInterval = mYPositions.length - mXOneOffset;
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0, yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);

    }
}
