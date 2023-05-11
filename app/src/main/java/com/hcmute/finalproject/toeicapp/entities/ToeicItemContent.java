package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicItemContent {

    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String contentType;
    private String content;
    private ToeicStorage toeicStorage;
    private ToeicQuestionGroup toeicQuestionGroupEntityQuestionContent;
    private ToeicQuestionGroup toeicQuestionGroupEntityTranscript;

//    constructor
    public ToeicItemContent() {}
    public ToeicItemContent(@NonNull Integer id, Integer serverId, String contentType, String content, ToeicStorage toeicStorage, ToeicQuestionGroup toeicQuestionGroupEntityQuestionContent, ToeicQuestionGroup toeicQuestionGroupEntityTranscript) {
        this.serverId = id;
        this.contentType = contentType;
        this.content = content;
        this.toeicStorage = toeicStorage;
        this.toeicQuestionGroupEntityQuestionContent = toeicQuestionGroupEntityQuestionContent;
        this.toeicQuestionGroupEntityTranscript = toeicQuestionGroupEntityTranscript;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ToeicStorage getToeicStorage() {
        return toeicStorage;
    }

    public void setToeicStorage(ToeicStorage toeicStorage) {
        this.toeicStorage = toeicStorage;
    }

    public ToeicQuestionGroup getToeicQuestionGroupEntityQuestionContent() {
        return toeicQuestionGroupEntityQuestionContent;
    }

    public void setToeicQuestionGroupEntityQuestionContent(ToeicQuestionGroup toeicQuestionGroupEntityQuestionContent) {
        this.toeicQuestionGroupEntityQuestionContent = toeicQuestionGroupEntityQuestionContent;
    }

    public ToeicQuestionGroup getToeicQuestionGroupEntityTranscript() {
        return toeicQuestionGroupEntityTranscript;
    }

    public void setToeicQuestionGroupEntityTranscript(ToeicQuestionGroup toeicQuestionGroupEntityTranscript) {
        this.toeicQuestionGroupEntityTranscript = toeicQuestionGroupEntityTranscript;
    }

}
