package com.hcmute.finalproject.toeicapp.model;

import java.io.Serializable;

public class ToeicAnswerChoice implements Serializable {
    private String label;
    private String content;
    private String explain;

    public ToeicAnswerChoice() {

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
