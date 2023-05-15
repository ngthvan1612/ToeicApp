package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonTestFooterComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicGroupItemViewModel;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponentBase;
import com.hcmute.finalproject.toeicapp.components.part.ToeicPartComponentFactory;
import com.hcmute.finalproject.toeicapp.services.learn.ToeicTestGradeService;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;

import java.util.ArrayList;
import java.util.List;

public class ToeicTestListQuestionsActivity extends GradientActivity {
    CommonHeaderComponent commonHeaderComponent;
    private List<ToeicGroupItemViewModel> toeicQuestionGroupViews = new ArrayList<>();
    private ViewPager viewPager;
    private Integer partId;
    private ViewPagerAdapter adapter;
    private CommonTestFooterComponent commonTestFooterComponent = null;
    private ToeicTestGradeService toeicTestGradeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_test_list_questions);

        this.toeicTestGradeService = new ToeicTestGradeService();
        this.commonTestFooterComponent = findViewById(R.id.activity_toeic_test_list_questions_footer);
        this.commonHeaderComponent = findViewById(R.id.activity_toeic_test_list_questions_header_title);
        this.setCommonHeaderComponent();

        this.viewPager = findViewById(R.id.activity_toeic_test_list_questions_view_pager);
        this.adapter = new ViewPagerAdapter();
        this.viewPager.setAdapter(adapter);


        toeicQuestionGroupViews.addAll(this.loadToeicQuestionGroupsFromIntent());
        this.partId = this.getPartIdFromIntent();

        this.viewPager.setOffscreenPageLimit(toeicQuestionGroupViews.size());
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

        this.commonTestFooterComponent.setOnMenuClickListener(new CommonTestFooterComponent.OnMenuClickListener() {
            @Override
            public void onPreviousMenuClicked() {
                final Integer currentItemId = viewPager.getCurrentItem();
                final String componentTag = "c-" + currentItemId;
                viewPager.setCurrentItem(currentItemId-1,true);
            }

            @Override
            public void onDictionaryMenuClicked() {

            }

            @Override
            public void onExplainMenuClicked() {
                final Integer currentItemId = viewPager.getCurrentItem();
                final String componentTag = "c-" + currentItemId;
                ToeicPartComponent toeicPartComponent = viewPager.findViewWithTag(componentTag);
                toeicPartComponent.showExplain();

            }

            @Override
            public void onNextMenuClicked() {
                final Integer currentItemId = viewPager.getCurrentItem();

                if(currentItemId + 1 == toeicQuestionGroupViews.size()) {
                    List<GradeToeicResult> listResultForEachQuestionGroups = new ArrayList<>();

                    for (int i = 0; i < toeicQuestionGroupViews.size(); ++i) {
                        final String tag = "c-" + i;
                        final ToeicPartComponent component = viewPager.findViewWithTag(tag);
                        assert component != null;
                        final GradeToeicResult result = component.calculateScore();
                        listResultForEachQuestionGroups.add(result);
                    }

                    GradeToeicResult gradeToeicResult = toeicTestGradeService.mergeResult(listResultForEachQuestionGroups);

                    Intent intent = new Intent(ToeicTestListQuestionsActivity.this, ResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", gradeToeicResult);
                    intent.putExtras(bundle);

                    startActivityForResult(intent, 1234);
                }
                else {
                    viewPager.setCurrentItem(currentItemId+1,true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            if (data.getStringExtra("select").equals("continue")) {
                finish();
            }
            else {
                for (int i = 0; i < toeicQuestionGroupViews.size(); ++i) {
                    final String tag = "c-" + i;
                    ToeicPartComponent component = viewPager.findViewWithTag(tag);
                    assert component != null;
                    component.showExplain();
                }
            }
        }
    }

    private Integer getPartIdFromIntent() {
        final Intent intent = getIntent();
        return intent.getIntExtra("partId", 0);
    }

    private void setCommonHeaderComponent() {
        Integer partId = getPartIdFromIntent();
        if (partId == 1) {
            commonHeaderComponent.setTitle("Part 1 - Photographs");
        } else if (partId == 2) {
            commonHeaderComponent.setTitle("Part 2 - Q & R");
        } else if (partId == 3) {
            commonHeaderComponent.setTitle("Part 3 - Conversations");
        } else if (partId == 4) {
            commonHeaderComponent.setTitle("Part 4 - Short Talks");
        } else if (partId == 5) {
            commonHeaderComponent.setTitle("Part 5 - Incomplete sentences");
        } else if (partId == 6) {
            commonHeaderComponent.setTitle("Part 6 - Text completion");
        } else if (partId == 7) {
            commonHeaderComponent.setTitle("Part 7 - Reading comprehension");
        }
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