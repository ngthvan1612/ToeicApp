package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.services.learn.ToeicTestGradeService;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ToeicPartComponentBase extends LinearLayout implements ToeicPartComponent {
    private List<ToeicQuestion> questions;
    private List<AnswerSelectionComponent> answerSelectionComponents;
    private ToeicTestGradeService toeicTestGradeService;
    private int partNumber;

    public ToeicPartComponentBase(Context context) {
        this(context, null);
    }

    protected void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public ToeicPartComponentBase(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public ToeicPartComponentBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.toeicTestGradeService = new ToeicTestGradeService();
        this.answerSelectionComponents = new ArrayList<>();
    }

    protected void setListQuestions(@NonNull List<ToeicQuestion> questions) {
        this.questions = questions;
    }

    protected void addAnswerSelectionComponent(@NonNull AnswerSelectionComponent component) {
        this.answerSelectionComponents.add(component);
    }

    @Override
    public GradeToeicResult calculateScore() {
        return this.toeicTestGradeService.gradePart(
                this.answerSelectionComponents
                        .stream()
                        .map(component -> {
                            GradeToeicPayload payload = new GradeToeicPayload();

                            payload.setPartNumber(this.partNumber);
                            payload.setCorrectAnswer(component.getCorrectAnswer());
                            payload.setQuestionNumber(component.getToeicQuestion().getQuestionNumber());
                            if (component.getCurrentChoice() != null) {
                                payload.setUserAnswer(component.getCurrentChoice().getLabel());
                            }
                            else {
                                payload.setUserAnswer("");
                            }

                            return payload;
                        })
                        .collect(Collectors.toList()),
                this.getTotalQuestions()
        );
    }
}
