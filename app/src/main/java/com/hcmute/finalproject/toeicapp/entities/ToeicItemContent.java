package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys={
        @ForeignKey(
                entity = ToeicQuestionGroup.class,
                parentColumns = "id",
                childColumns = "toeicQuestionGroupEntityQuestionContentId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = ToeicQuestionGroup.class,
                parentColumns = "id",
                childColumns = "toeicQuestionGroupEntityTranscriptId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
}
)
public class ToeicItemContent {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String contentType;
    private String content;

    private Integer toeicQuestionGroupEntityQuestionContentId;
    private Integer toeicQuestionGroupEntityTranscriptId;

//    constructor
    public ToeicItemContent() {}
    public ToeicItemContent(@NonNull Integer id, Integer serverId, String contentType, String content, Integer toeicQuestionGroupEntityQuestionContentId, Integer toeicQuestionGroupEntityTranscriptId) {
        this.id = id;
        this.serverId = serverId;
        this.contentType = contentType;
        this.content = content;
        this.toeicQuestionGroupEntityQuestionContentId = toeicQuestionGroupEntityQuestionContentId;
        this.toeicQuestionGroupEntityTranscriptId = toeicQuestionGroupEntityTranscriptId;
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
    public Integer getToeicQuestionGroupEntityQuestionContentId() {
        return toeicQuestionGroupEntityQuestionContentId;
    }

    public void setToeicQuestionGroupEntityQuestionContentId(Integer toeicQuestionGroupEntityQuestionContentId) {
        this.toeicQuestionGroupEntityQuestionContentId = toeicQuestionGroupEntityQuestionContentId;
    }

    public Integer getToeicQuestionGroupEntityTranscriptId() {
        return toeicQuestionGroupEntityTranscriptId;
    }

    public void setToeicQuestionGroupEntityTranscriptId(Integer toeicQuestionGroupEntityTranscriptId) {
        this.toeicQuestionGroupEntityTranscriptId = toeicQuestionGroupEntityTranscriptId;
    }

}
