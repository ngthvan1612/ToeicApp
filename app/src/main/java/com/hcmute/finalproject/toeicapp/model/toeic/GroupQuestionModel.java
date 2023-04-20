package com.hcmute.finalproject.toeicapp.model.toeic;

public class GroupQuestionModel {
    private int partId;
    private String name;
    private int numOfQuestions;

    public GroupQuestionModel(int partId, String name, int numOfQuestions) {
        this.partId = partId;
        this.name = name;
        this.numOfQuestions = numOfQuestions;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }
}
