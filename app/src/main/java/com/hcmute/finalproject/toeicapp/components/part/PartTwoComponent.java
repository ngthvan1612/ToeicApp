package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.graphics.Bitmap;
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

import java.io.File;
import java.util.List;

public class PartTwoComponent extends ToeicPartComponentBase {
    private CommonHeaderComponent commonHeaderComponent;
    public AnswerSelectionComponent answerSelectionComponent;

    public PartTwoComponent(Context context) {
        this(context, null);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_two, this);

        this.answerSelectionComponent = view.findViewById(R.id.component_part_two_answer);
    }

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {
        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        ToeicQuestionDao toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        ToeicAnswerChoiceDao toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        ToeicQuestion toeicQuestion = toeicQuestionDao.getToeicQuestionByQuestionGroppId(toeicQuestionGroup.getId()).get(0);
        List<ToeicAnswerChoice> choices = toeicAnswerChoiceDao.getByQuestionId(toeicQuestion.getId());

        ToeicItemContentDao toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        List<ToeicItemContent> toeicItemContentList = toeicItemContentDao.getItemContentByGroupId(toeicQuestionGroup.getId());

        this.answerSelectionComponent.setCorrectAnswer(toeicQuestion.getCorrectAnswer());
        this.answerSelectionComponent.setToeicAnswerChoices(choices);
    }

    @Override
    public void showExplain() {
        this.answerSelectionComponent.setShowExplain(!this.answerSelectionComponent.isShowExplain());
    }
    @Override
    public Integer getNumberCorrectAnswer() {
        final ToeicAnswerChoice currentChoice = this.answerSelectionComponent.getCurrentChoice();
        if (currentChoice != null) {
            if (currentChoice.getLabel().equals(this.answerSelectionComponent.getCorrectAnswer())) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public Integer getTotalQuestions() {
        return 1;
    }
}
