package com.hcmute.finalproject.toeicapp.assets.backend;

import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;

public class AndroidAnswerChoice {
    private Integer serverId;

    private String label;

    private String content;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
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

    private String explain;

}
