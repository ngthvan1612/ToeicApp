package com.hcmute.finalproject.toeicapp.model.toeic;

public class ToeicPartItemView {
    private int id;
    private int serverId;
    private int partNumber;
    private String name;
    private int numOfQuestions;
    private boolean isDownloaded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToeicPartItemView() { }

    public ToeicPartItemView(int id, int partNumber, String name, int numOfQuestions) {
        this.isDownloaded = false;
        this.id = id;
        this.partNumber = partNumber;
        this.name = name;
        this.numOfQuestions = numOfQuestions;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }
}
