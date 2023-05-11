package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicPart {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private Integer partNumber;
    private ToeicFullTest toeicFullTest;


//    constructor
    public ToeicPart() {}
    public ToeicPart(Integer id,Integer serverId, Integer partNumber, ToeicFullTest toeicFullTest) {
        this.serverId = id;
        this.partNumber = partNumber;
        this.toeicFullTest = toeicFullTest;
    }

//    getter setter
    @NonNull
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

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public ToeicFullTest getToeicFullTest() {
        return toeicFullTest;
    }

    public void setToeicFullTest(ToeicFullTest toeicFullTest) {
        this.toeicFullTest = toeicFullTest;
    }
}
