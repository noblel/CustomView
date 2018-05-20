package com.noblel.customview;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private String[] items = {"文章", "笔记", "视频", "图片", "娱乐", "同城", "游戏", "今日", "热点"};
    private LinearLayout mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mIndicators = new ArrayList<>();
        mIndicatorContainer = findViewById(R.id.indicator_view);
        mViewPager = findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);

                if (position + 1 >= mIndicators.size()) return;
                ColorTrackTextView right = mIndicators.get(position + 1);
                right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                right.setCurrentProgress(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        for (String item : items) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(item);
            colorTrackTextView.setLayoutParams(params);
            mIndicatorContainer.addView(colorTrackTextView);
            mIndicators.add(colorTrackTextView);
        }
    }
}
