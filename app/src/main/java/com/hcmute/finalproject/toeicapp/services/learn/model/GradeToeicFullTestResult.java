package com.hcmute.finalproject.toeicapp.services.learn.model;

import java.io.Serializable;

public class GradeToeicFullTestResult implements Serializable {
    private Integer totalScoreMin;
    private Integer totalScoreMax;

    public GradeToeicFullTestResult() {

    }

    public Integer getTotalScoreMin() {
        return totalScoreMin;
    }

    public void setTotalScoreMin(Integer totalScoreMin) {
        this.totalScoreMin = totalScoreMin;
    }

    public Integer getTotalScoreMax() {
        return totalScoreMax;
    }

    public void setTotalScoreMax(Integer totalScoreMax) {
        this.totalScoreMax = totalScoreMax;
    }
}
