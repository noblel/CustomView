package com.noblel.slideview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author Noblel
 */

public class SlideLayout extends FrameLayout {

    private static final String TAG = SlideLayout.class.getSimpleName();
    /**
     * 内容视图
     */
    private View mContentView;
    /**
     * 菜单视图
     */
    private View mMenuView;

    private int mContentWidth;
    private int mMenuWidth;
    private int mContentHeight;
    private float mStartX;
    /**
     * Down事件的坐标
     */
    private float mDownX;
    /**
     * 滚动器
     */
    private Scroller mScroller;

    private OnStateChangeListener mOnStateChangeListener;


    public SlideLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    /**
     * 布局文件加载完成回调的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mMenuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //指定菜单位置

        mMenuView.layout(mContentWidth, 0, mContentWidth + mMenuWidth, mContentHeight);
        mContentWidth = mContentView.getMeasuredWidth();
        mMenuWidth = mMenuView.getMeasuredWidth();
        mContentHeight = mContentView.getMeasuredHeight();
        Log.e(TAG, "onLayout: " + mContentWidth);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                //计算偏移量
                float distanceX = endX - mStartX;
                Log.e(TAG, "onTouchEvent: "+ getScrollX());
                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > mMenuWidth) {
                    toScrollX = mMenuWidth;
                }
                scrollTo(toScrollX, 0);
                //重新赋值
                mStartX = event.getX();
                //在X轴滑动的距离
                float DX = Math.abs(endX - mDownX);
                if (DX > 5) {
                    //水平方向滑动 响应侧滑  反拦截-事件给slideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX();
                if (totalScrollX < mMenuWidth / 2) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下坐标
                mDownX = mStartX = event.getX();
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                //重新赋值
                mStartX = event.getX();
                //在X轴滑动的距离
                float DX = Math.abs(endX - mDownX);
                intercept = DX > 8;
                break;
        }
        return intercept;
    }

    public void openMenu() {
        int distanceX = mMenuWidth - getScrollX();
        mScroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        //强制刷新
        invalidate();
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onOpen(this);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        int distanceX = 0 - getScrollX();
        mScroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        //强制刷新
        invalidate();
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onClose(this);
        }
    }

    /**
     * View在draw()的过程中调用
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    /**
     * 监听slideLayout状态的改变
     */
    public interface OnStateChangeListener {
        void onClose(SlideLayout layout);

        void onDown(SlideLayout layout);

        void onOpen(SlideLayout layout);
    }

    /**
     * 设置slideLayout状态的监听
     */
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }
}
