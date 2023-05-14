package com.hcmute.finalproject.toeicapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.part.PartOnePhotographsComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicAnswerChoice;
import java.util.ArrayList;
import java.util.List;

public class PartOnePhotographsActivity extends GradientActivity {
    private static final int NUMBER_OF_PAGES = 5;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        viewPager = findViewById(R.id.activity_home_view_pager);
        this.initViewPager();
    }
    private void initViewPager() {
        viewPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        viewPager.setAdapter(new ViewPagerNavigationAdapter());
    }
    private List<TestToeicAnswerChoice> getSamplePart1Choices() {
        List<TestToeicAnswerChoice> choices = new ArrayList<>();

        // A.
        TestToeicAnswerChoice a = new TestToeicAnswerChoice();
        a.setContent("");
        a.setLabel("A");
        a.setExplain("She's tying her shoelaces.");
        choices.add(a);

        // B
        TestToeicAnswerChoice b = new TestToeicAnswerChoice();
        b.setContent("");
        b.setLabel("B");
        b.setExplain("She's holding a cup.");
        choices.add(b);

        // C.
        TestToeicAnswerChoice c = new TestToeicAnswerChoice();
        c.setContent("");
        c.setLabel("C");
        c.setExplain("She's reading under an umbrella.");
        choices.add(c);

        // D.
        TestToeicAnswerChoice d = new TestToeicAnswerChoice();
        d.setContent("");
        d.setLabel("D");
        d.setExplain("She's jogging through a park.");
        choices.add(d);

        return choices;
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
            CommonHeaderComponent commonHeaderComponent = new CommonHeaderComponent(PartOnePhotographsActivity.this);
            container.addView(component);
            container.addView(commonHeaderComponent);


            final AnswerSelectionComponent answerSelectionComponent = container.findViewById(R.id.component_part_one_photographs_answer_selection);
            final List<TestToeicAnswerChoice> toeicAnswerChoices = getSamplePart1Choices();
            answerSelectionComponent.setToeicAnswerChoices(toeicAnswerChoices);

            commonHeaderComponent = findViewById(R.id.common_part_one_header_title);
            Intent intent = new Intent(PartOnePhotographsActivity.this,HomeActivity.class);
            commonHeaderComponent.handleBackToHomepage(intent);
            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


    }
}
