package com.hcmute.finalproject.toeicapp.dto.backend;

import com.hcmute.finalproject.toeicapp.dto.backend.AndroidAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;

import java.util.List;

public class AndroidToeicQuestion {
    private Integer serverId;

    private Integer questionNumber;

    private String correctAnswer;

    private String content;

    private List<AndroidAnswerChoice> choices;

    public AndroidToeicQuestion(ToeicQuestion entity) {
        this.serverId = entity.getId();
        this.questionNumber = entity.getQuestionNumber();
        this.correctAnswer = entity.getCorrectAnswer();
        this.content = entity.getContent();
    }
}
