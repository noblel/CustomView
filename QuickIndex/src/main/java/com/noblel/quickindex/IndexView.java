package com.noblel.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Noblel
 */

public class IndexView extends View {

    private Paint mPaint;
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int mItemHeight;
    private String mCurrentTouchWord;

    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener listener) {
        mListener = listener;
    }

    public interface LetterTouchListener {
        void touch(CharSequence letter, boolean isTouch);
    }

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(12));
        mPaint.setColor(Color.BLUE);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算宽度 = 左右的padding + 字母的宽度(取决画笔大小)
        int textWidth = (int) mPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mItemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / words.length;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y / mItemHeight);//字母索引
                if (index < 0)
                    index = 0;
                if (index > words.length - 1)
                    index = words.length - 1;
                if (!words[index].equals(mCurrentTouchWord)) {
                    mCurrentTouchWord = words[index];
                    if (mListener != null)
                        mListener.touch(mCurrentTouchWord, true);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null)
                    mListener.touch(mCurrentTouchWord, false);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < words.length; i++) {
            int letterCenterY = i * mItemHeight + mItemHeight / 2 + getPaddingTop();
            //基线
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;
            int textWidth = (int) mPaint.measureText(words[i]);
            int x = getWidth() / 2 - textWidth / 2;
            //当前字母高亮(最好用两个画笔)
            if (words[i].equals(mCurrentTouchWord)) {
                mPaint.setColor(Color.RED);
                canvas.drawText(words[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(words[i], x, baseLine, mPaint);
            }
        }
    }
}
