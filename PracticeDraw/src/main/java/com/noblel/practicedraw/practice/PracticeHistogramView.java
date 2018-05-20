package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         使用各种 Canvas.drawXXX() 方法画直方图
 */
public class PracticeHistogramView extends View {
    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();

    public PracticeHistogramView(Context context) {
        super(context);
    }

    public PracticeHistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(100, 50, 100, 600, mPaint);
        canvas.drawLine(100, 600, 1000, 600, mPaint);
        mRect.set(140, 595, 240, 600);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.GREEN);
        mRect.set(260, 580, 360, 600);
        canvas.drawRect(mRect, mPaint);
        mRect.set(380, 50, 480, 600);
        canvas.drawRect(mRect, mPaint);
        mRect.set(500, 300, 600, 600);
        canvas.drawRect(mRect, mPaint);
        mRect.set(620, 400, 720, 600);
        canvas.drawRect(mRect, mPaint);

    }
}
