package com.hcmute.finalproject.toeicapp.components.part;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

public class ToeicGroupItemViewModel {
    private ToeicQuestionGroup group;
    private Integer partNumber;

    public ToeicGroupItemViewModel() { }

    public ToeicQuestionGroup getGroup() {
        return group;
    }

    public void setGroup(ToeicQuestionGroup group) {
        this.group = group;
    }

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }
}
