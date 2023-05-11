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

    private ToeicStorage toeicTopicImage;

//    constructor
    public ToeicVocabularyTopic(){}
    public ToeicVocabularyTopic(@NonNull Integer id, Integer serverId, String name, ToeicStorage toeicTopicImage) {
        this.serverId = id;
        this.name = name;
        this.toeicTopicImage = toeicTopicImage;
    }

    //    getter setter
    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
    public Integer getServerId() {return serverId;}

    public void setServerId(Integer serverId) {this.serverId = serverId;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
