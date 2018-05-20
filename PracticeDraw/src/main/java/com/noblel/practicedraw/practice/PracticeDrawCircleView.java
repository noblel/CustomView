package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
 */
public class PracticeDrawCircleView extends View {

    private Paint mSolidPaint;
    private Paint mRingPaint;
    private Paint mBlueRingPaint;
    private Paint mWidthRingPaint;

    public PracticeDrawCircleView(Context context) {
        this(context, null);
    }

    public PracticeDrawCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PracticeDrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSolidPaint = new Paint();
        mRingPaint = new Paint();
        //设置模式
        mRingPaint.setStyle(Paint.Style.STROKE);
        mBlueRingPaint = new Paint();
        mBlueRingPaint.setColor(Color.BLUE);
        mWidthRingPaint = new Paint();
        mWidthRingPaint.setStyle(Paint.Style.STROKE);
        //设置大小
        mWidthRingPaint.setStrokeWidth(dip2px(getContext(),20));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 4, getHeight() / 4, getHeight() / 4, mSolidPaint);
        canvas.drawCircle(getWidth() * 3 / 4, getHeight() / 4, getHeight() / 4, mRingPaint);
        canvas.drawCircle(getWidth() / 4, getHeight() * 3 / 4, getHeight() / 4, mBlueRingPaint);
        canvas.drawCircle(getWidth() * 3 / 4, getHeight() * 3 / 4, getHeight() / 4, mWidthRingPaint);
    }
}
