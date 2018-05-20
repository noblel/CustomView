package com.noblel.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ColorTrackTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView =  findViewById(R.id.textView);
    }

    public void leftToRight(View view) {
        mTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mTextView.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        mTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mTextView.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

    public void test(View view) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);
    }
}
