package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicQuestionGroup {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private ToeicPart toeicPart;

//    constructor
    public ToeicQuestionGroup(){}
    public ToeicQuestionGroup(Integer id, Integer serverId, ToeicPart toeicPart) {
        this.serverId = id;
        this.toeicPart = toeicPart;
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

    public ToeicPart getToeicPart() {
        return toeicPart;
    }

    public void setToeicPart(ToeicPart toeicPart) {
        this.toeicPart = toeicPart;
    }
}
