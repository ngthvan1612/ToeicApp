package com.hcmute.finalproject.toeicapp.network.backend;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

public class AndroidToeicPart {
    private Integer serverId;

    private Integer partNumber;
    private List<AndroidToeicQuestionGroup> groups;


    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public List<AndroidToeicQuestionGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<AndroidToeicQuestionGroup> groups) {
        this.groups = groups;
    }

    public AndroidToeicPart(ToeicPart entity) {
        this.serverId = entity.getId();
        this.partNumber = entity.getPartNumber();
    }
}
