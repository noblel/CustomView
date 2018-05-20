package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 * 使用 canvas.drawOval() 方法画椭圆
 */
public class PracticeDrawOvalView extends View {
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF(100,100,500,200);

    public PracticeDrawOvalView(Context context) {
        super(context);
    }

    public PracticeDrawOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(mRectF,mPaint);
    }
}
