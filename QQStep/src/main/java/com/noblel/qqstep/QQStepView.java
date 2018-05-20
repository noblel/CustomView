package com.noblel.qqstep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 */

public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20; //代表20px
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOutPaint, mInnerPaint, mTextPaint;

    /** 总共的步数和当前的步数 */
    private int mStepMax;
    private int mCurrentStep;
    private RectF mRectF;
    private Rect mTextBounds;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性,编写attrs.xml
        //3.在布局中使用
        //4.在自定义View中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = (int) array.getDimension(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);

        //To Avoid object allocations during draw/layout operations
        mRectF = new RectF();
        mTextBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度高度不一致,取最小值,确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    /**
     * 画外圆弧, 画内圆弧
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;
        mRectF.left = center - radius;
        mRectF.top = center - radius;
        mRectF.right = center + radius;
        mRectF.bottom = center + radius;
        canvas.drawArc(mRectF, 135, 270, false, mOutPaint);

        //判断最大步数
        if (mStepMax == 0)
            throw new ExceptionInInitializerError("you may not setStepMax() or the StepMax is 0");

        //画内圆弧
        float sweepAngle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(mRectF, 135, sweepAngle * 270, false, mInnerPaint);

        //画文字
        String str = mCurrentStep + "步";
        mTextPaint.getTextBounds(str, 0, str.length(), mTextBounds);
        int dx = center - mTextBounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        //基线 baseLine
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = center + dy;
        canvas.drawText(str, dx, baseLine, mTextPaint);
    }

    /**
     * 设置最大的步数
     */
    public void setStepMax(int stepMax) {
        mStepMax = stepMax;
    }

    /**
     * 设置当前的步数
     * @param currentStep 当前步数
     */
    public synchronized void setCurrentStep(int currentStep) {
        if (currentStep > mStepMax){
            return;
        }
        mCurrentStep = currentStep;
        invalidate();
    }
}
