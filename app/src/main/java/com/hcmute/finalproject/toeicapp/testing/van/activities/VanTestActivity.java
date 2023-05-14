package com.hcmute.finalproject.toeicapp.testing.van.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.learnvocab.LearnVocabularyQuestionComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicAnswerChoice;

import java.util.ArrayList;
import java.util.List;

public class VanTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test);

        final ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(this);
        final ToeicVocabularyDao dao = toeicAppDatabase.getToeicVocabularyDao();
        ToeicVocabulary vocabulary = dao.getOne(10);

        LearnVocabularyQuestionComponent component = findViewById(R.id.activity_van_test_question);
        component.loadVocabulary(vocabulary);

        component.setViewMode(LearnVocabularyQuestionComponent.MODE_WRONG_ANSWER);
    }

    int counter = 0;

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

    private List<TestToeicAnswerChoice> getSamplePart3Choices() {
        List<TestToeicAnswerChoice> choices = new ArrayList<>();

        // A.
        TestToeicAnswerChoice a = new TestToeicAnswerChoice();
        a.setContent("Product quality testing");
        a.setLabel("A");
        a.setExplain("Kiểm tra chất lượng sản phẩm");
        choices.add(a);

        // B
        TestToeicAnswerChoice b = new TestToeicAnswerChoice();
        b.setContent("Candidates for a job");
        b.setLabel("B");
        b.setExplain("Ứng cử viên cho 1 công việ");
        choices.add(b);

        // C.
        TestToeicAnswerChoice c = new TestToeicAnswerChoice();
        c.setContent("Contracts with vendors");
        c.setLabel("C");
        c.setExplain("Hợp đồng với khách hàng");
        choices.add(c);

        // D.
        TestToeicAnswerChoice d = new TestToeicAnswerChoice();
        d.setContent("Design modifications");
        d.setLabel("D");
        d.setExplain("Sửa đổi thiết kế");
        choices.add(d);

        return choices;
    }
}