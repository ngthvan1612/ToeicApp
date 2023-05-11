package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = ToeicQuestion.class,
                parentColumns = "id",
                childColumns = "toeicQuestionId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class ToeicAnswerChoice {

    @PrimaryKey
    @NonNull
    private Integer id;

    private Integer serverId;
    private String label;
    private String content;
    private String explain;
    private Integer toeicQuestionId;

    //    constructor
    public ToeicAnswerChoice(){}
    public ToeicAnswerChoice(@NonNull Integer id, Integer serverId, String label, String content, String explain, Integer toeicQuestionId) {
        this.id = id;
        this.serverId = serverId;
        this.label = label;
        this.content = content;
        this.explain = explain;
        this.toeicQuestionId = toeicQuestionId;
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

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getToeicQuestionId() {
        return toeicQuestionId;
    }

    public void setToeicQuestionId(Integer toeicQuestionId) {
        this.toeicQuestionId = toeicQuestionId;
    }
}
