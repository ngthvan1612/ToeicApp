package com.hcmute.finalproject.toeicapp.components.part;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;

public interface ToeicPartComponent {

    void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup);
    void showExplain();
    GradeToeicResult calculateScore();
    default Integer getNumberCorrectAnswer() { return 0; }
    default Integer getTotalQuestions() { return 0; }
}
