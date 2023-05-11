package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicQuestion {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private Integer questionNumber;
    private String correctAnswer;
    private String content;
    private ToeicQuestionGroup toeicQuestionGroup;

//    constructor
    public ToeicQuestion() {}
    public ToeicQuestion(@NonNull Integer id, Integer serverId, Integer questionNumber, String correctAnswer, String content, ToeicQuestionGroup toeicQuestionGroup) {
        this.serverId = id;
        this.questionNumber = questionNumber;
        this.correctAnswer = correctAnswer;
        this.content = content;
        this.toeicQuestionGroup = toeicQuestionGroup;
    }

//    getter setter
    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

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

    public ToeicQuestionGroup getToeicQuestionGroup() {
        return toeicQuestionGroup;
    }

    public void setToeicQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {
        this.toeicQuestionGroup = toeicQuestionGroup;
    }
}
