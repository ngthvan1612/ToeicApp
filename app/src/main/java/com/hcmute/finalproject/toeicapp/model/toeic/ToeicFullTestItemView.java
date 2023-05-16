package com.hcmute.finalproject.toeicapp.model.toeic;

public class ToeicFullTestItemView {
    private int id;
    private int serverId;
    private String fullName;
    private boolean isDownloaded;

    public ToeicFullTestItemView() { }

    public ToeicFullTestItemView(int id, int serverId, String fullName) {
        this.id = id;
        this.serverId = serverId;
        this.fullName = fullName;
        this.isDownloaded = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
