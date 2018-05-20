package com.noblel.customviewpager;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private MyViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private int[] mIds = {
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.myViewPager);
        mRadioGroup = findViewById(R.id.rg_main);

        //添加页面
        for (int mId : mIds) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mId);
            //添加到MyViewPager这个ViewGroup中
            mViewPager.addView(imageView);
        }

        //添加测试页面
        View testView = View.inflate(this, R.layout.test, null);
        //子视图没有显示是因为没有测量
        mViewPager.addView(testView, 2);

        for (int i = 0; i < mViewPager.getChildCount(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            if (i == 0) {
                button.setChecked(true);
            }
            //添加到RadioGroup
            mRadioGroup.addView(button);
        }

        //设置RadioGroup选中状态变化
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * checkId相当于坐标
             */
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //根据下标位置定位到具体的某个页面
                mViewPager.scrollToPager(checkedId);
            }
        });

        //设置页面监听改变
        mViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            @Override
            public void onScrollToPager(int position) {
                mRadioGroup.check(position);
            }
        });
    }
}
