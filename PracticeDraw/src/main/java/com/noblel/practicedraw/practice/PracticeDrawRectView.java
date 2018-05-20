package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 *         使用 canvas.drawRect() 方法画矩形
 */
public class PracticeDrawRectView extends View {
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();

    public PracticeDrawRectView(Context context) {
        super(context);
    }

    public PracticeDrawRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.set(getWidth() / 3, getHeight() / 3, getWidth() * 2 / 3, getHeight() * 2 / 3);
        canvas.drawRect(mRect, mPaint);
    }
}
