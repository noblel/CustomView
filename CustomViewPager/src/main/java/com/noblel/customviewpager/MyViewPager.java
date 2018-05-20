package com.noblel.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author Noblel
 */

public class MyViewPager extends ViewGroup {
    /**
     * 手势识别
     * 1.定义
     * 2.实例化-把想要的方法给重写
     * 3.onTouchEvent()传递给手势识别器
     */
    private GestureDetector mDetector;
    //当前页面下标位置
    private int mCurrentIndex;

    //可以使用系统自带的Scroller
    private MyScroller mScroller;

    private OnPagerChangeListener mOnPagerChangeListener;

    private float mStartX;
    private float mDownX;
    private float mDownY;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        mScroller = new MyScroller();
        //实例化手势识别器
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, getScrollY());
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在屏幕的位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        mDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //2.新坐标
                float endX = event.getX();
                int tempIndex = mCurrentIndex;
                if ((mStartX - endX) > getWidth() / 2) {
                    //显示下一个画面
                    tempIndex++;
                } else if ((endX - mStartX) > getWidth() / 2) {
                    //显示上一个画面
                    tempIndex--;
                }
                //根据下标位置移动到指定页面
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值,根据位置移动到指定页面
     *
     * @param tempIndex
     */
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        mCurrentIndex = tempIndex;
        //判断是否为空
        if (mOnPagerChangeListener != null) {
            mOnPagerChangeListener.onScrollToPager(mCurrentIndex);
        }
        //需要平滑的距离
        int distanceX = mCurrentIndex * getWidth() - getScrollX();
        //移动到指定的位置
//        mScroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX));
        mScroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            float currX = mScroller.getCurrX();
            scrollTo((int) currX, 0);
            invalidate();
        }
    }

    public interface OnPagerChangeListener {
        /**
         * 当页面改变时候回调这个方法
         *
         * @param position 当前页面的下标
         */
        void onScrollToPager(int position);
    }

    public void setOnPagerChangeListener(OnPagerChangeListener l) {
        mOnPagerChangeListener = l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        mDetector.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.记录结束值
                float endX = ev.getX();
                float endY = ev.getY();
                //3.计算绝对值
                float distanceX = Math.abs(endX - mDownX);
                float distanceY = Math.abs(endY - mDownY);
                if (distanceX > distanceY && distanceX > 10) {
                    result = true;
                } else {
                    scrollToPager(mCurrentIndex);
                }
                break;
        }
        return result;
    }
}
