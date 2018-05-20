package com.noblel.practicedraw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Noblel
 */
public class PracticeDrawColorView extends View {
    public PracticeDrawColorView(Context context) {
        super(context);
    }

    public PracticeDrawColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeDrawColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
    }

}
