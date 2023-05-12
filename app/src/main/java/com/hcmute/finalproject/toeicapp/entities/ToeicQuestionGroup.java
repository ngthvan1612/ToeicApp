package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = ToeicPart.class,
                parentColumns = "id",
                childColumns = "toeicPartId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class ToeicQuestionGroup {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private Integer toeicPartId;

//    constructor
    public ToeicQuestionGroup(){}
    public ToeicQuestionGroup(Integer id, Integer serverId, Integer toeicPartId) {
        this.serverId = id;
        this.toeicPartId = toeicPartId;
    }

//    getter setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }
    public Integer getToeicPartId() {
        return toeicPartId;
    }

    public void setToeicPartId(Integer toeicPartId) {
        this.toeicPartId = toeicPartId;
    }

}
