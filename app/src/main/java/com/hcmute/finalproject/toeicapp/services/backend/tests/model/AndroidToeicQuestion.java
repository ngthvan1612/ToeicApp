package com.hcmute.finalproject.toeicapp.services.backend.tests.model;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;

import java.util.List;

public class AndroidToeicQuestion {
    private Integer serverId;

    private Integer questionNumber;

    private String correctAnswer;

    private String content;
    private List<AndroidAnswerChoice> choices;


    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AndroidAnswerChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<AndroidAnswerChoice> choices) {
        this.choices = choices;
    }

    public AndroidToeicQuestion(ToeicQuestion entity) {
        this.serverId = entity.getId();
        this.questionNumber = entity.getQuestionNumber();
        this.correctAnswer = entity.getCorrectAnswer();
        this.content = entity.getContent();
    }
}
