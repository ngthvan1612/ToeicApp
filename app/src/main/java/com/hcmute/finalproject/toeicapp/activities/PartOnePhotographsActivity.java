package com.hcmute.finalproject.toeicapp.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.part_one.PartOnePhotographsComponent;

public class PartOnePhotographsActivity extends GradientActivity {
    private static final int NUMBER_OF_PAGES = 5;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_part_one);
        viewPager = findViewById(R.id.activity_home_view_pager);
        this.initViewPager();

    }
    private void initViewPager() {
        viewPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        viewPager.setAdapter(new PartOnePhotographsActivity.ViewPagerNavigationAdapter());

    }
    private class ViewPagerNavigationAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PartOnePhotographsComponent component = new PartOnePhotographsComponent(PartOnePhotographsActivity.this);
            container.addView(component);
//            component.setTag(getLayoutTagByPosition(position));
            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
