package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys= {
        @ForeignKey(
                entity = ToeicFullTest.class,
                parentColumns = "id",
                childColumns = "toeicFullTestId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class ToeicPart {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private Integer partNumber;
    private Integer toeicFullTestId;
    private Boolean downloaded = false;


    //    constructor
    public ToeicPart() {}
    public ToeicPart(Integer id,Integer serverId, Integer partNumber, Integer toeicFullTestId,Boolean downloaded) {
        this.serverId = id;
        this.partNumber = partNumber;
        this.toeicFullTestId = toeicFullTestId;
        this.downloaded = downloaded;
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
    public Integer getToeicFullTestId() {
        return toeicFullTestId;
    }

    public void setToeicFullTestId(Integer toeicFullTestId) {
        this.toeicFullTestId = toeicFullTestId;
    }
    public Boolean getDownloaded() {
        return downloaded;
    }
    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }


}
