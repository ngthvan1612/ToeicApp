package com.hcmute.finalproject.toeicapp.services.backend.tests.model;

import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;

import java.util.List;

public class AndroidToeicFullTest {
    private Integer serverId;

    private String fullName;

    private List<AndroidToeicPart> parts;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<AndroidToeicPart> getParts() {
        return parts;
    }

    public void setParts(List<AndroidToeicPart> parts) {
        this.parts = parts;
    }
    public AndroidToeicFullTest(ToeicFullTest entity) {
        this.serverId = entity.getId();
        this.fullName = entity.getFullName();
    }
}
