package com.hcmute.finalproject.toeicapp.model.toeic;

import java.io.Serializable;

public class TestToeicAnswerChoice implements Serializable {
    private String label;
    private String content;
    private String explain;

    public TestToeicAnswerChoice() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
