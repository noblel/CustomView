package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         使用各种 Canvas.drawXXX() 方法画饼图
 */
public class PracticePieChartView extends View {
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF(150, 50, 850, 750);

    public PracticePieChartView(Context context) {
        super(context);
    }

    public PracticePieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticePieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mRectF, -45, 45, true, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(mRectF, 6, 10, true, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(mRectF, 18, 50, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(mRectF, 70, 110, true, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mRectF.set(140, 30, 840, 730);
        canvas.drawArc(mRectF, -180, 130, true, mPaint);
    }
}
