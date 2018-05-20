package com.noblel.qqstep;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQStepView qqStepView = findViewById(R.id.step_view);
        qqStepView.setStepMax(2000);

        //属性动画
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int) currentStep);
            }
        });
        animator.start();
    }
}
