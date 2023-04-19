package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.components.part_one.PartOnePhotographsComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPart;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackground;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundFactory;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundService;

import java.util.ArrayList;
import java.util.List;

public class ToeicTestListQuestionsActivity extends AppCompatActivity {
    private List<ToeicQuestionGroup> toeicQuestionGroups = new ArrayList<>();
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

        toeicQuestionGroups.addAll(this.loadToeicQuestionGroupsFromIntent());
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

    public List<ToeicQuestionGroup> loadToeicQuestionGroupsFromIntent() {
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        return (List<ToeicQuestionGroup>)bundle.get("toeic-group-questions");
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return toeicQuestionGroups.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final ToeicQuestionGroup toeicQuestionGroup = toeicQuestionGroups.get(position);

            if (toeicQuestionGroup.getType().equals("1")) {
                PartOnePhotographsComponent component = new PartOnePhotographsComponent(ToeicTestListQuestionsActivity.this);
                container.addView(component);
                component.loadToeicQuestionGroup(partId, toeicQuestionGroup);
                component.setTag("c-" + position);
                return component;
            }

            return null;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}