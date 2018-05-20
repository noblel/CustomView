package com.noblel.customviewpager;

import android.os.SystemClock;

/**
 * @author Noblel
 */

public class MyScroller {

    /**
     * X轴的起始坐标
     */
    private float mStartX;
    /**
     * Y轴的起始坐标
     */
    private float mStartY;

    /**
     * 在X轴要移动的距离
     */
    private int mDistanceX;
    /**
     * 在Y轴要移动的距离
     */
    private int mDistanceY;
    /**
     * 开始的时间
     */
    private long mStartTime;

    /**
     * 总时间
     */
    private long mTotalTime = 500;
    /**
     * 是否移动完成
     * false没有移动完成
     * true:移动结束
     */
    private boolean mIsFinish;
    private float mCurrX;

    /**
     * 得到坐标
     */
    public float getCurrX() {
        return mCurrX;
    }

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        mStartY = startY;
        mStartX = startX;
        mDistanceX = distanceX;
        mDistanceY = distanceY;
        mStartTime = SystemClock.uptimeMillis();//系统开机时间
        mIsFinish = false;
    }

    /**
     * @return 是否正在移动
     */
    public boolean computeScrollOffset() {
        if (mIsFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        //这一小段所花的时间
        long passTime = endTime - mStartTime;
        if (passTime < mTotalTime) {
            //还没有移动结束
            //移动这个一小段对应的距离
            float distanceSmallX = passTime * mDistanceX / mTotalTime;
            mCurrX = mStartX + distanceSmallX;
        } else {
            //移动结束
            mIsFinish = true;
            mCurrX = mStartX + mDistanceX;
        }
        return true;
    }
}
