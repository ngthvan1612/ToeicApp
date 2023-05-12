package com.hcmute.finalproject.toeicapp.dto.backend;
import com.hcmute.finalproject.toeicapp.dto.backend.AndroidToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

public class AndroidToeicPart {
    private Integer serverId;

    private Integer partNumber;

    private List<AndroidToeicQuestionGroup> groups;

    public AndroidToeicPart(ToeicPart entity) {
        this.serverId = entity.getId();
        this.partNumber = entity.getPartNumber();
    }
}
