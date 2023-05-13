package com.hcmute.finalproject.toeicapp.model.toeic;

import java.io.Serializable;
import java.util.List;

public class TestToeicPart implements Serializable {
    private Integer partId;
    private String slug;
    private List<TestToeicQuestionGroup> toeicQuestionGroups;
    private Boolean downloaded = false;

    public TestToeicPart() {

    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<TestToeicQuestionGroup> getToeicQuestionGroups() {
        return toeicQuestionGroups;
    }

    public void setToeicQuestionGroups(List<TestToeicQuestionGroup> toeicQuestionGroups) {
        this.toeicQuestionGroups = toeicQuestionGroups;
    }
    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }
}
