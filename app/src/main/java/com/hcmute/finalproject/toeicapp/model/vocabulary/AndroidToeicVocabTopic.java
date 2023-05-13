package com.hcmute.finalproject.toeicapp.model.vocabulary;

import androidx.annotation.NonNull;

import java.util.List;

public class AndroidToeicVocabTopic {
    private Integer serverId;

    private String topicName;

    private String imageFileName;

    private List<AndroidToeicVocabWord> words;

    public AndroidToeicVocabTopic() {

    }

    public AndroidToeicVocabTopic(Integer serverId, String topicName, String imageFileName, List<AndroidToeicVocabWord> words) {
        this.serverId = serverId;
        this.topicName = topicName;
        this.imageFileName = imageFileName;
        this.words = words;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public List<AndroidToeicVocabWord> getWords() {
        return words;
    }

    public void setWords(List<AndroidToeicVocabWord> words) {
        this.words = words;
    }
}
