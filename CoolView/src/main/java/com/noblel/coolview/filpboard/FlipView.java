package com.noblel.coolview.filpboard;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.noblel.coolview.R;

/**
 * @author Noblel
 */
public class FlipView extends View {
    private Paint mPaint;
    private Camera mCamera;
    private Bitmap mFlipSrc;

    //Z轴方向（平面内）旋转的角度
    private float mDegreeZ;
    private float mDegreeY;
    //不变的那一半，Y轴方向旋转角度
    private float mFixDegreeY;
    private boolean isPlay;

    public FlipView(Context context) {
        this(context, null);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlipView);
        BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(R.styleable.FlipView_flipSrc);
        array.recycle();

        if (drawable != null) {
            mFlipSrc = drawable.getBitmap();
        } else {
            mFlipSrc = BitmapFactory.decodeResource(getResources(), R.drawable.flipboard);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCamera = new Camera();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        mCamera.setLocation(0, 0, newZ);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int bitmapWidth = mFlipSrc.getWidth();
        int bitmapHeight = mFlipSrc.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;
        canvas.save();
        mCamera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-mDegreeZ);
        mCamera.rotateY(mDegreeY);
        mCamera.applyToCanvas(canvas);

        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(mDegreeZ);
        canvas.translate(-centerX, -centerY);
        mCamera.restore();
        canvas.drawBitmap(mFlipSrc, x, y, mPaint);
        canvas.restore();

        //画不变换的另一半
        canvas.save();
        mCamera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-mDegreeZ);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
        mCamera.rotateY(mFixDegreeY);
        mCamera.applyToCanvas(canvas);
        canvas.rotate(mDegreeZ);
        canvas.translate(-centerX, -centerY);
        mCamera.restore();
        canvas.drawBitmap(mFlipSrc, x, y, mPaint);
        canvas.restore();
    }

    /**
     * 复位
     */
    public void reset() {
        mDegreeY = 0;
        mFixDegreeY = 0;
        mDegreeZ = 0;
    }

    private void startAnim() {
        ObjectAnimator degreeY = ObjectAnimator.ofFloat(this, "degreeY", 0, -45);
        degreeY.setDuration(1000);
        degreeY.setStartDelay(500);

        ObjectAnimator degreeZ = ObjectAnimator.ofFloat(this, "degreeZ", 0, 270);
        degreeZ.setDuration(800);
        degreeZ.setStartDelay(500);

        ObjectAnimator fixDegreeY = ObjectAnimator.ofFloat(this, "fixDegreeY", 0, 30);
        fixDegreeY.setDuration(500);
        fixDegreeY.setStartDelay(500);

        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reset();
            }
        });
        set.playSequentially(degreeY, degreeZ, fixDegreeY);
        if (!isPlay){
            set.start();
            isPlay = true;
        } else {
            set.end();
            isPlay = false;
            set.start();
        }
    }


    private void setFixDegreeY(float fixDegreeY) {
        mFixDegreeY = fixDegreeY;
        invalidate();
    }

    private void setDegreeY(float degreeY) {
        mDegreeY = degreeY;
        invalidate();
    }

    private void setDegreeZ(float degreeZ) {
        mDegreeZ = degreeZ;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        mFlipSrc = bitmap;
        invalidate();
    }
}
