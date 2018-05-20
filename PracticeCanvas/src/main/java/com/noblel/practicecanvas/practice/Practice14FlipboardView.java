package com.noblel.practicecanvas.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.noblel.practicecanvas.R;

/**
 * camera的变换是带着整个三维坐标系的移动的，所以要用Canvas的translate不要用camera的
 * 变换结束之后才会做投影，不是一旋转就做投影
 * camera的x轴(从右往左)顺时针，y(从上往下看)z轴(从前往后看)逆时针
 */
public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        //设置插值器
        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //视图中心点
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        //Bitmap的左顶点
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        // 第一遍绘制：上半部分
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        if (degree < 90) {
            //小于90度就在下面绘制
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        } else {
            //否则就在上面绘制
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        camera.save();
        //相机沿x轴向上外旋转
        camera.rotateX(degree);
        //画布平移到左顶点
        canvas.translate(centerX, centerY);
        //进行投影
        camera.applyToCanvas(canvas);
        //画布再平移回来
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
