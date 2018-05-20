package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 * canvas.drawLine() 方法画直线
 */
public class PracticeDrawLineView extends View {
    private Paint mPaint = new Paint();

    public PracticeDrawLineView(Context context) {
        super(context);
    }

    public PracticeDrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(200, 200, 800, 500, mPaint);
    }
}
