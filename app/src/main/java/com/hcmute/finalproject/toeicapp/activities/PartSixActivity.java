package com.hcmute.finalproject.toeicapp.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.QuestionSentenceComponent;
import com.hcmute.finalproject.toeicapp.components.part_six.PartSixComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicAnswerChoice;

import java.util.ArrayList;
import java.util.List;

public class PartSixActivity extends GradientActivity {
    private static final int NUMBER_OF_PAGES = 5;
    private ViewPager viewPager;
    private QuestionSentenceComponent questionSentenceComponent;

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
    private List<ToeicAnswerChoice> getSamplePart1Choices() {
        List<ToeicAnswerChoice> choices = new ArrayList<>();

        // A.
        ToeicAnswerChoice a = new ToeicAnswerChoice();
        a.setContent("");
        a.setLabel("A");
        a.setExplain("She's tying her shoelaces.");
        choices.add(a);

        // B
        ToeicAnswerChoice b = new ToeicAnswerChoice();
        b.setContent("");
        b.setLabel("B");
        b.setExplain("She's holding a cup.");
        choices.add(b);

        // C.
        ToeicAnswerChoice c = new ToeicAnswerChoice();
        c.setContent("");
        c.setLabel("C");
        c.setExplain("She's reading under an umbrella.");
        choices.add(c);

        // D.
        ToeicAnswerChoice d = new ToeicAnswerChoice();
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
            PartSixComponent component = new PartSixComponent(PartSixActivity.this);
            container.addView(component);

            final AnswerSelectionComponent answerSelectionComponent1 = container.findViewById(R.id.component_part_six_answer_selection_1);
            final AnswerSelectionComponent answerSelectionComponent2 = container.findViewById(R.id.component_part_six_answer_selection_2);
            final AnswerSelectionComponent answerSelectionComponent3 = container.findViewById(R.id.component_part_six_answer_selection_3);
            final List<ToeicAnswerChoice> toeicAnswerChoices = getSamplePart1Choices();
            answerSelectionComponent1.setToeicAnswerChoices(toeicAnswerChoices);
            answerSelectionComponent2.setToeicAnswerChoices(toeicAnswerChoices);
            answerSelectionComponent3.setToeicAnswerChoices(toeicAnswerChoices);

            questionSentenceComponent = container.findViewById(R.id.component_part_six_question_sentence);
            questionSentenceComponent.setQuestionDescription
                    ("<h2>hello</h2>");

            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
