package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicAnswerChoice {

    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String label;
    private String content;
    private String explain;
    private ToeicQuestion toeicQuestion;

    //    constructor
    public ToeicAnswerChoice(){}
    public ToeicAnswerChoice(@NonNull Integer id, Integer serverId, String label, String content, String explain, ToeicQuestion toeicQuestion) {
        this.serverId = id;
        this.label = label;
        this.content = content;
        this.explain = explain;
        this.toeicQuestion = toeicQuestion;
    }

    //    getter setter
    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
