package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 * canvas.drawArc() 方法画弧形和扇形
 */
public class PracticeDrawArcView extends View {
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF(200, 100, 800, 500);

    public PracticeDrawArcView(Context context) {
        super(context);
    }

    public PracticeDrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL); // 填充模式
        //useCenter如果设为true则会出现一个锥子形的，false就是连接两个端点
        canvas.drawArc(mRectF, -110, 100, true, mPaint); // 绘制扇形
        canvas.drawArc(mRectF, 20, 140, false, mPaint); // 绘制弧形
        mPaint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(mRectF, 180, 60, false, mPaint); // 绘制不封口的弧形
    }
}
