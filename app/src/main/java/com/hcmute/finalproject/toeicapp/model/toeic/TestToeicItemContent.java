package com.hcmute.finalproject.toeicapp.model.toeic;

import java.io.Serializable;

public class TestToeicItemContent implements Serializable {
    private String type;
    private String content;

    public TestToeicItemContent() {

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
