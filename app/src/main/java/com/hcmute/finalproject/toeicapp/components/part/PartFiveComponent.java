package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

import java.util.List;

public class PartFiveComponent extends ToeicPartComponentBase {
    private CommonHeaderComponent commonHeaderComponent;
    public AnswerSelectionComponent answerSelectionComponent;

    public PartFiveComponent(Context context) {
        this(context, null);
    }

    public PartFiveComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartFiveComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setPartNumber(5);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_five, this);
        this.answerSelectionComponent = view.findViewById(R.id.component_part_five_answer_selection);
        super.addAnswerSelectionComponent(this.answerSelectionComponent);
    }

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {
        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        ToeicQuestionDao toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        ToeicAnswerChoiceDao toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        ToeicQuestion toeicQuestion = toeicQuestionDao.getToeicQuestionByQuestionGroppId(toeicQuestionGroup.getId()).get(0);
        List<ToeicAnswerChoice> choices = toeicAnswerChoiceDao.getByQuestionId(toeicQuestion.getId());

        this.answerSelectionComponent.setQuestionTitle(toeicQuestion.getQuestionNumber()+". "+toeicQuestion.getContent());
        this.answerSelectionComponent.setCorrectAnswer(toeicQuestion.getCorrectAnswer());
        this.answerSelectionComponent.setToeicAnswerChoices(choices);
        this.answerSelectionComponent.setToeicQuestion(toeicQuestion);
    }

    @Override
    public void showExplain() {
        this.answerSelectionComponent.setShowExplain(!this.answerSelectionComponent.isShowExplain());
    }

    @Override
    public Integer getTotalQuestions() {
        return 1;
    }

}
