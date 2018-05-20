package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 * canvas.drawRoundRect() 方法画圆角矩形
 */
public class PracticeDrawRoundRectView extends View {
    private Paint mPaint = new Paint();

    public PracticeDrawRoundRectView(Context context) {
        super(context);
    }

    public PracticeDrawRoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawRoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        //rx和ry是圆角的横向半径和纵向半径
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, mPaint);
    }
}
