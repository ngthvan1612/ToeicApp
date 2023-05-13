package com.hcmute.finalproject.toeicapp.entities;

import androidx.room.*;

import androidx.annotation.NonNull;

@Entity
public class ToeicVocabularyTopic {
    @PrimaryKey
    @NonNull
    private Integer id;

    private Integer serverId;

    private String name;

    private String imageFileName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}