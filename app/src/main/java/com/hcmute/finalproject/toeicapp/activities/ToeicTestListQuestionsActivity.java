package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.part.PartOnePhotographsComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicGroupItemViewModel;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponentBase;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponentFactory;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToeicTestListQuestionsActivity extends GradientActivity {
    private List<ToeicGroupItemViewModel> toeicQuestionGroupViews = new ArrayList<>();
    private ViewPager viewPager;
    private Integer partId;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_test_list_questions);

        this.viewPager = findViewById(R.id.activity_toeic_test_list_questions_view_pager);
        this.adapter = new ViewPagerAdapter();
        this.viewPager.setAdapter(adapter);

        toeicQuestionGroupViews.addAll(this.loadToeicQuestionGroupsFromIntent());
        this.partId = this.getPartIdFromIntent();

        this.viewPager.setOffscreenPageLimit(0);
        this.adapter.notifyDataSetChanged();

        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private Integer getPartIdFromIntent() {
        final Intent intent = getIntent();
        return intent.getIntExtra("part-id", 0);
    }

    public List<ToeicGroupItemViewModel> loadToeicQuestionGroupsFromIntent() {
        final Intent intent = getIntent();
        final String json = intent.getStringExtra("question-data");
        final Gson gson = new Gson();
        return List.of(gson.fromJson(json, ToeicGroupItemViewModel[].class));
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return toeicQuestionGroupViews.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final ToeicGroupItemViewModel viewModel = toeicQuestionGroupViews.get(position);
            final ToeicPartComponentBase component = ToeicPartComponentFactory.createInstance(
                    viewModel,
                    ToeicTestListQuestionsActivity.this
            );

            container.addView(component);
            component.setTag("c-" + position);
            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}