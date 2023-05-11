package com.hcmute.finalproject.toeicapp.testing.huong.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.GradientActivity;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.part_one.PartOnePhotographsComponent;
import com.hcmute.finalproject.toeicapp.components.part_three.PartThreeComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicAnswerChoice;

import java.util.ArrayList;
import java.util.List;

public class PartThreeActivity extends GradientActivity {
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
    private List<ToeicAnswerChoice> getSamplePart3Choices() {
        List<ToeicAnswerChoice> choices = new ArrayList<>();

        // A.
        ToeicAnswerChoice a = new ToeicAnswerChoice();
        a.setContent("Product quality testing");
        a.setLabel("A");
        a.setExplain("Kiểm tra chất lượng sản phẩm");
        choices.add(a);

        // B
        ToeicAnswerChoice b = new ToeicAnswerChoice();
        b.setContent("Candidates for a job");
        b.setLabel("B");
        b.setExplain("Ứng cử viên cho 1 công việ");
        choices.add(b);

        // C.
        ToeicAnswerChoice c = new ToeicAnswerChoice();
        c.setContent("Contracts with vendors");
        c.setLabel("C");
        c.setExplain("Hợp đồng với khách hàng");
        choices.add(c);

        // D.
        ToeicAnswerChoice d = new ToeicAnswerChoice();
        d.setContent("Design modifications");
        d.setLabel("D");
        d.setExplain("Sửa đổi thiết kế");
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
            PartThreeComponent component =
                    new PartThreeComponent(PartThreeActivity.this);
            container.addView(component);
//            final AnswerSelectionComponent answerSelectionComponent = container.findViewById(R.id.component_part_three_photographs_answer_selection);
//            final List<ToeicAnswerChoice> toeicAnswerChoices = getSamplePart3Choices();
//            answerSelectionComponent.setToeicAnswerChoices(toeicAnswerChoices);
            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
