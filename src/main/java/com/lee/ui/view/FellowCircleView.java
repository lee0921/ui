package com.lee.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 *  图形跟踪小圆环
 */

public class FellowCircleView extends View {
    private Paint mPaint;
    private Path mPath;
    private float [] mCurrentPosition = {300, 500};
    private PathMeasure mPathMeasure;

    public FellowCircleView(Context context) {
        this(context, null);
    }

    public FellowCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FellowCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mPath.moveTo(300, 500);
        mPath.lineTo(800, 500);
        mPath.lineTo(800, 1000);
        mPath.lineTo(300, 1000);
        mPath.lineTo(300, 500);

        mPathMeasure = new PathMeasure(mPath, true);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], 10 , mPaint);


    }

    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, mPathMeasure.getLength());
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value  = (float) animation.getAnimatedValue();
                boolean posTan = mPathMeasure.getPosTan(value, mCurrentPosition, null);
                if (posTan){
                    postInvalidate();
                }

            }
        });

        animator.start();
    }
}
