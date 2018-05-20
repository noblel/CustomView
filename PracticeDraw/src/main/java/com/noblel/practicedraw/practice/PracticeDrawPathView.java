package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PracticeDrawPathView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    {
        mPath.addArc(200, 200, 400, 400, -225, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
        mPath.lineTo(400, 542);
    }

    public PracticeDrawPathView(Context context) {
        super(context);
    }

    public PracticeDrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
