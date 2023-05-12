package com.hcmute.finalproject.toeicapp.dto.backend;

public class AndroidItemContent {
    private Integer storageServerId;

    private String contentType;

    private String rawContent;
    public AndroidItemContent(Integer storageServerId, String contentType, String rawContent) {
        this.storageServerId = storageServerId;
        this.contentType = contentType;
        this.rawContent = rawContent;
    }
}
