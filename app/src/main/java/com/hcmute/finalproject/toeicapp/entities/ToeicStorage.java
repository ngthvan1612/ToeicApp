package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class ToeicStorage {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String fileName;

//    constructor
    public ToeicStorage(){}
    public ToeicStorage(Integer id, Integer serverId, String fileName) {
        this.serverId = id;
        this.fileName = fileName;
    }

//    getter setter
    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
