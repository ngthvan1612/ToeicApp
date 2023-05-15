package com.hcmute.finalproject.toeicapp.components.part;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPartResult;

public interface ToeicPartComponent {
    void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup);
    void showExplain();

    GradeToeicPartResult calculateScore();
}
