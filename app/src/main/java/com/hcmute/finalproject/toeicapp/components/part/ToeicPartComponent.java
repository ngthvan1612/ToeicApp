package com.hcmute.finalproject.toeicapp.components.part;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

public interface ToeicPartComponent {

    void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup);
    void showExplain();
    String getAnswer();
    String getSelectedChoice();
    default Integer getNumberCorrectAnswer() { return 0; }
    default Integer getTotalQuestions() { return 0; }
}
