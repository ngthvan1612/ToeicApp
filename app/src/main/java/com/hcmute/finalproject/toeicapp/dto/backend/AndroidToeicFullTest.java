package com.hcmute.finalproject.toeicapp.dto.backend;

import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

public class AndroidToeicFullTest {
    private Integer serverId;

    private String fullName;

    private List<AndroidToeicPart> parts;

    public AndroidToeicFullTest(ToeicFullTest entity) {
        this.serverId = entity.getId();
        this.fullName = entity.getFullName();
    }
}
