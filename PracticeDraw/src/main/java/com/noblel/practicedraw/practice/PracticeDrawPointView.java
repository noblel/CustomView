package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         使用 canvas.drawPoint() 方法画点
 *         一个圆点，一个方点
 *         圆点和方点的切换使用 paint.setStrokeCap(cap)：`ROUND` 是圆点，`BUTT` 或 `SQUARE` 是方点
 */
public class PracticeDrawPointView extends View {
    private Paint mPaint = new Paint();

    public PracticeDrawPointView(Context context) {
        super(context);
    }

    public PracticeDrawPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(100);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(80, 80 ,mPaint);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(200, 80 ,mPaint);
    }
}
