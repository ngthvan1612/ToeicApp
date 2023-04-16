package com.hcmute.finalproject.toeicapp.testing.van.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicAnswerChoice;

import java.util.ArrayList;
import java.util.List;

public class VanTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test);

        final AnswerSelectionComponent answerSelectionComponent = findViewById(R.id.activity_van_test_answer_selection_test);
        final List<ToeicAnswerChoice> toeicAnswerChoices = this.getSamplePart3Choices();
        final Button btnShowAnswer = findViewById(R.id.activity_van_test_answer_btn_show_answer);

        answerSelectionComponent.setToeicAnswerChoices(toeicAnswerChoices);


        btnShowAnswer.setOnClickListener(view -> {
            counter++;
            answerSelectionComponent.setQuestionTitle("Question " + counter);
            answerSelectionComponent.setShowExplain(!answerSelectionComponent.isShowExplain());
        });
    }

    int counter = 0;

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
}