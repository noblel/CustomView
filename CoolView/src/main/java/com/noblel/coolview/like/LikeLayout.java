package com.noblel.coolview.like;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.noblel.coolview.R;

/**
 * @author Noblel
 */
public class LikeLayout extends LinearLayout {
    public LikeLayout(Context context) {
        super(context);
    }

    public LikeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = findViewById(R.id.like_button);
        final EditText editText = findViewById(R.id.set_num);
        View makeNum = findViewById(R.id.make_num);

        final LikeNumView likeNumView = findViewById(R.id.like_number_view);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                likeNumView.performClick();
            }
        });
        makeNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                likeNumView.setLikeNum(Integer.valueOf(editText.getText().toString()));
                likeNumView.invalidate();
            }
        });
    }
}
