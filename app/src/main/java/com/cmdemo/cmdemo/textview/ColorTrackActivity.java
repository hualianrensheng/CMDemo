package com.cmdemo.cmdemo.textview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cmdemo.cmdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ColorTrackActivity extends AppCompatActivity {


    private String[] items = {"孙悟空", "猪八戒", "沙悟净", "唐三藏", "白龙马"};
    private LinearLayout mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);


        mIndicators = new ArrayList<>();
        mIndicatorContainer = (LinearLayout)findViewById(R.id.indicator_view);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
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
                left.setDirectory(ColorTrackTextView.Directory.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);

                try{
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirectory(ColorTrackTextView.Directory.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }catch (Exception e){

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;

            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setTextSize(40);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(layoutParams);

            mIndicatorContainer.addView(colorTrackTextView);
            mIndicators.add(colorTrackTextView);
        }
    }
}