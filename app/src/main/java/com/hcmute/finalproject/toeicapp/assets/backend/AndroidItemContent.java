package com.hcmute.finalproject.toeicapp.assets.backend;

public class AndroidItemContent {
    private Integer storageServerId;
    private String contentType;

    private String rawContent;

    public Integer getStorageServerId() {
        return storageServerId;
    }

    public void setStorageServerId(Integer storageServerId) {
        this.storageServerId = storageServerId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }


    public AndroidItemContent(Integer storageServerId, String contentType, String rawContent) {
        this.storageServerId = storageServerId;
        this.contentType = contentType;
        this.rawContent = rawContent;
    }
}
