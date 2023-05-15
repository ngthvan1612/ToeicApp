package com.hcmute.finalproject.toeicapp.services.learn.model;

import java.io.Serializable;

public class GradeToeicResult implements Serializable {
    private Integer numberOfCorrectQuestions;
    private Integer totalQuestions;
    private GradeToeicRate rate;

    public GradeToeicResult() {

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
