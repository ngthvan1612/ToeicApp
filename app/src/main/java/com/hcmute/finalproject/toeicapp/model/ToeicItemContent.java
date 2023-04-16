package com.hcmute.finalproject.toeicapp.model;

import java.io.Serializable;

public class ToeicItemContent implements Serializable {
    private String type;
    private String content;

    public ToeicItemContent() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
