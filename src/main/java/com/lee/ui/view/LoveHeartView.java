package com.lee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *  桃心(贝塞尔曲线)
 */

public class LoveHeartView extends View{
    private Paint mPaint;
    private Path mPath;


    public LoveHeartView(Context context) {
        this(context, null);
    }

    public LoveHeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        mPath.moveTo(300, 300);//必须利用此方法确定起始点，否则会以(0, 0)为起始点
        mPath.quadTo(450, 200, 300, 400); // 参数依次为 控制点x轴坐标 控制点y轴坐标 结束点x轴坐标 结束点y轴坐标 (坐标均为绝对值)
        canvas.drawPath(mPath, mPaint);
        mPath.moveTo(300, 300);
        mPath.quadTo(150, 200,300, 400);
        canvas.drawPath(mPath, mPaint);

    }
}
