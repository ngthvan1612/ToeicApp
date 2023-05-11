package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicVocabWord {

    @PrimaryKey
    @NonNull
    private Integer id;

    private Integer serverId;

    private String english;

    private String vietnamese;

    private String pronounce;

    private String exampleEnglish;

    private String exampleVietnamese;

    private ToeicStorage wordImage;

    private ToeicVocabularyTopic topic;

//    constructor
    public ToeicVocabWord() {}
    public ToeicVocabWord(@NonNull Integer id, Integer serverId, String english, String vietnamese, String pronounce, String exampleEnglish, String exampleVietnamese, ToeicStorage wordImage, ToeicVocabularyTopic topic) {
        this.serverId = id;
        this.english = english;
        this.vietnamese = vietnamese;
        this.pronounce = pronounce;
        this.exampleEnglish = exampleEnglish;
        this.exampleVietnamese = exampleVietnamese;
        this.wordImage = wordImage;
        this.topic = topic;
    }

//    getter setter
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

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getExampleEnglish() {
        return exampleEnglish;
    }

    public void setExampleEnglish(String exampleEnglish) {
        this.exampleEnglish = exampleEnglish;
    }

    public String getExampleVietnamese() {
        return exampleVietnamese;
    }

    public void setExampleVietnamese(String exampleVietnamese) {
        this.exampleVietnamese = exampleVietnamese;
    }

    public ToeicStorage getWordImage() {
        return wordImage;
    }

    public void setWordImage(ToeicStorage wordImage) {
        this.wordImage = wordImage;
    }

    public ToeicVocabularyTopic getTopic() {
        return topic;
    }

    public void setTopic(ToeicVocabularyTopic topic) {
        this.topic = topic;
    }
}
