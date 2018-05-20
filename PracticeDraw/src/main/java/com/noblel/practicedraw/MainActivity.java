package com.noblel.practicedraw;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<PageModel> mPageModels = new ArrayList<>();

    {
        mPageModels.add(new PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color));
        mPageModels.add(new PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle));
        mPageModels.add(new PageModel(R.layout.sample_rect, R.string.title_draw_rect, R.layout.practice_rect));
        mPageModels.add(new PageModel(R.layout.sample_point, R.string.title_draw_point, R.layout.practice_point));
        mPageModels.add(new PageModel(R.layout.sample_oval, R.string.title_draw_oval, R.layout.practice_oval));
        mPageModels.add(new PageModel(R.layout.sample_line, R.string.title_draw_line, R.layout.practice_line));
        mPageModels.add(new PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect, R.layout.practice_round_rect));
        mPageModels.add(new PageModel(R.layout.sample_arc, R.string.title_draw_arc, R.layout.practice_arc));
        mPageModels.add(new PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path));
        mPageModels.add(new PageModel(R.layout.sample_histogram, R.string.title_draw_histogram, R.layout.practice_histogram));
        mPageModels.add(new PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart, R.layout.practice_pie_chart));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = mPageModels.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes,pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return mPageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(mPageModels.get(position).titleRes);
            }
        });
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class PageModel {
        @LayoutRes
        int sampleLayoutRes;
        @StringRes
        int titleRes;
        @LayoutRes int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
