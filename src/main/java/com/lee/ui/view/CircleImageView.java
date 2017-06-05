package com.lee.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lee.ui.R;
import com.lee.ui.utils.ScreenUtils;

/**
 * 圆形头像、图片
 *
 * 1.创建位图
 * 2.将位图scale到目标大小
 * 3.创建BitmapShader并设置给paint
 * 4.使用该paint绘制目标大小的圆
 *
 */

public class CircleImageView extends View{
    private Context mContext;
    private Bitmap mBitmap;
    private Paint mPaint;

    private static final int BITMAP_SCALE_WIDTH = 500;
    private static final int BITMAP_SCALE_HEIGHT = 500;
    private static final int[] mColors = new int[] { Color.TRANSPARENT, Color.TRANSPARENT, Color.WHITE};
    private static final float[] mPositions = new float[] { 0, 0.6f, 1f};

    private int bitmapWidth;
    private int bitmapHeight;
    private int radius;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.smile1)).getBitmap();
        mBitmap = Bitmap.createScaledBitmap(mBitmap, BITMAP_SCALE_WIDTH, BITMAP_SCALE_HEIGHT, false);
        bitmapWidth = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();
        radius = Math.min(bitmapWidth, bitmapHeight) / 2;

        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        RadialGradient radialGradient = new RadialGradient(bitmapWidth / 2, bitmapHeight / 2, radius, mColors, mPositions, Shader.TileMode.CLAMP);

        ComposeShader composSharder = new ComposeShader(shader, radialGradient, PorterDuff.Mode.SRC_OVER);

        mPaint.setShader(composSharder);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(ScreenUtils.getScreenWidth(mContext) / 2 - bitmapWidth / 2, ScreenUtils.getScreenHeight(mContext) / 2 - bitmapHeight / 2);
        canvas.drawCircle(bitmapWidth / 2,
                bitmapHeight / 2,
                radius, mPaint);

//        canvas.drawBitmap(mBitmap,0, 0, mPaint);
    }
}
