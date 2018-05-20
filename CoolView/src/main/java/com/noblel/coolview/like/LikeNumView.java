package com.noblel.coolview.like;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.noblel.coolview.DisplayUtils;
import com.noblel.coolview.R;

import static android.view.View.MeasureSpec.AT_MOST;

/**
 * @author Noblel
 */
public class LikeNumView extends View {
    public static final int NO_NINE = -1;
    private static final String TAG = LikeNumView.class.getSimpleName();
    private int mLikeNum = 0, mCorrectNum = 0;
    private int mNumColor = Color.BLACK;
    private String mCorrectNumStr;
    private String mLikeNumStr;
    private Paint mNumPaint;
    private float mYOffset;
    private float mNumSize;
    private boolean isLike = false;
    private int mWidth;
    private int mHeight;

    public LikeNumView(Context context) {
        this(context, null);
    }

    public LikeNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeNumView);
        mLikeNum = array.getInteger(R.styleable.LikeNumView_likeNum, mLikeNum);
        mNumSize = array.getDimension(R.styleable.LikeNumView_likeNumSize, DisplayUtils.sp2px(context, 16));
        mNumColor = array.getColor(R.styleable.LikeNumView_likeNumColor, mNumColor);
        mNumPaint = new Paint();
        mNumPaint.setAntiAlias(true);
        mNumPaint.setColor(mNumColor);
        mNumPaint.setTextSize(mNumSize);
        array.recycle();
        setLikeNum(mLikeNum);
        //TODO 重写OnTouchEvent,这里简单的使用下
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    unLike();
                } else {
                    like();
                }
            }
        });
    }

    public void setLikeNum(int num) {
        mLikeNum = num;
        mLikeNumStr = String.valueOf(num);
        mCorrectNum = num + 1;
        mCorrectNumStr = String.valueOf(mCorrectNum);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle data = new Bundle();
        data.putParcelable("superData", super.onSaveInstanceState());
        data.putInt("likeNum", mLikeNum);
        data.putFloat("numSize", mNumSize);
        data.putInt("numColor", mNumColor);
        return data;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle data = (Bundle) state;
        Parcelable superData = data.getParcelable("superData");
        super.onRestoreInstanceState(superData);
        mLikeNum = data.getInt("likeNum", mLikeNum);
        mNumSize = data.getFloat("numSize", DisplayUtils.sp2px(getContext(), 16f));
        mNumColor = data.getInt("numColor", mNumColor);
    }

    /**
     * 设置点赞
     */
    public void like() {
        isLike = true;
        mCorrectNum = mLikeNum + 1;
        //字体往上
        startAnim(0, -getHeight());
    }

    private void startAnim(int start, int end) {
        ObjectAnimator textUpAnimator = ObjectAnimator.ofFloat(this, "yOffset", start, end);
        textUpAnimator.setDuration(500);
        textUpAnimator.start();
    }

    /**
     * 取消点赞
     */
    public void unLike() {
        isLike = false;
        mCorrectNum = mLikeNum;
        //字体往下
        startAnim(-getHeight(), 0);
    }

    /**
     * 查找从右往左第一个非9位置
     *
     * @param number 查找的数
     * @return 1 代表第一个位置，0代表全部都是9，-1代表末尾不是9
     * 例如：
     * 如果是9，就返回0，如果是19，就返回1...
     * 如果是1999，那么会返回1
     * 如果是199989，就返回5
     * 如果是9999，那么会返回0
     * 如果是1234，会返回-1
     */
    private int findTheFirstNotNine(int number) {
        String s = String.valueOf(number);
        int locate = s.length() - 1;
        if ('9' != s.charAt(locate)) {
            return NO_NINE;
        }
        while ('9' == s.charAt(locate)) {
            locate--;
            if (locate < 0) {
                locate++;
                break;
            }
            if ('9' != s.charAt(locate)) {
                return locate + 1;
            }
        }
        return locate;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(getWidth()/2 ,0,getWidth() /2, getHeight(),mNumPaint);
        //测量文字大小
        float likeNumStrWidth = mNumPaint.measureText(mLikeNumStr);
        float correctNumStrWidth = mNumPaint.measureText(mCorrectNumStr);
        //确定基线
        Paint.FontMetricsInt fontMetricsInt = mNumPaint.getFontMetricsInt();
        int centerX = getWidth() / 2;
        //文字高度的一半到基线的距离
        //top表示基线到文字最上面的位置的距离 是个负值 bottom为基线到最下面的距离，为正值
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int bastLine = getHeight() / 2 + dy;
        //index位置后的文字需要变化
        float index;
        //变化的text
        String changeText;
        int theFirstNotNine = findTheFirstNotNine(mLikeNum);
        float everyCharWidth = mNumPaint.measureText(mLikeNumStr) / mLikeNumStr.length();
        if (theFirstNotNine == NO_NINE) {
            //没有9证明只需要变化最后一位即可
            index = everyCharWidth * (mLikeNumStr.length() - 1);
            changeText = mCorrectNumStr.substring(mCorrectNumStr.length() - 1, mCorrectNumStr.length());
            canvas.drawText(mLikeNumStr, 0, mLikeNumStr.length() - 1, centerX - likeNumStrWidth / 2, bastLine, mNumPaint);
            canvas.drawText(mLikeNumStr, mLikeNumStr.length() - 1, mLikeNumStr.length(), centerX + index- likeNumStrWidth / 2, bastLine + mYOffset, mNumPaint);
        } else if (theFirstNotNine > 1) {
            //大于1 说明变化的地方在中间位置
            index = everyCharWidth * (theFirstNotNine - 1);
            //需要变化的文字
            changeText = mCorrectNumStr.substring(theFirstNotNine - 1, mCorrectNumStr.length());
            //画左半部分不变的部分
            canvas.drawText(mLikeNumStr, 0, theFirstNotNine - 1, centerX - likeNumStrWidth / 2, bastLine, mNumPaint);
            //画右边变化的部分
            canvas.drawText(mLikeNumStr, theFirstNotNine - 1, mLikeNumStr.length(), centerX + index - likeNumStrWidth / 2, bastLine + mYOffset, mNumPaint);
        } else {
            //说明全部需要变化，包括9999，1999，9
            index = 0;
            changeText = mCorrectNumStr;
            canvas.drawText(mLikeNumStr, centerX - likeNumStrWidth / 2, bastLine + mYOffset, mNumPaint);
        }
        canvas.drawText(changeText, centerX + index - correctNumStrWidth / 2, bastLine + mYOffset + getHeight(), mNumPaint);
    }

    private void setYOffset(float YOffset) {
        mYOffset = YOffset;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mayBeWidth = (int) (mNumPaint.measureText(mCorrectNumStr) + getPaddingLeft() + getPaddingRight());
        mWidth = (int) (mNumPaint.measureText(mLikeNumStr) + getPaddingLeft() + getPaddingRight());
        mWidth = Math.max(mWidth, mayBeWidth);
        mHeight = (int) (mNumSize + getPaddingTop() + getPaddingBottom());
        mWidth = Math.max(mWidth, getSuggestedMinimumWidth());
        if (widthMode == AT_MOST && heightMode == AT_MOST) {
            //设置默认宽高
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
