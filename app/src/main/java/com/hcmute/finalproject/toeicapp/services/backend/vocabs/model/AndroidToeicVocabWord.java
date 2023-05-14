package com.hcmute.finalproject.toeicapp.services.backend.vocabs.model;

public class AndroidToeicVocabWord {
    private Integer serverId;

    private String english;

    private String vietnamese;

    private String pronounce;

    private String exampleEnglish;

    private String exampleVietnamese;

    private String audioFileName;

    private String imageFilename;

    public AndroidToeicVocabWord() {

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

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
