package com.hcmute.finalproject.toeicapp.services.learn.model;

import java.io.Serializable;
import java.util.List;

public class GradeToeicResult implements Serializable {
    private Integer numberOfSkipQuestions;
    private Integer numberOfCorrectQuestions;
    private Integer totalQuestions;
    private GradeToeicRate rate;
    private List<GradeToeicPayload> payloads;

    public GradeToeicResult() {

    }

    public List<GradeToeicPayload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<GradeToeicPayload> payloads) {
        this.payloads = payloads;
    }

    public Integer getNumberOfSkipQuestions() {
        return numberOfSkipQuestions;
    }

    public void setNumberOfSkipQuestions(Integer numberOfSkipQuestions) {
        this.numberOfSkipQuestions = numberOfSkipQuestions;
    }

    public Integer getNumberOfCorrectQuestions() {
        return numberOfCorrectQuestions;
    }

    public void setNumberOfCorrectQuestions(Integer numberOfCorrectQuestions) {
        this.numberOfCorrectQuestions = numberOfCorrectQuestions;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public GradeToeicRate getRate() {
        return rate;
    }

    public void setRate(GradeToeicRate rate) {
        this.rate = rate;
    }
}
