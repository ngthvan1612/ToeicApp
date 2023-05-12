package com.hcmute.finalproject.toeicapp.assets.backend;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import java.util.List;

public class AndroidToeicQuestionGroup {
    private Integer serverId;
    private List<AndroidToeicQuestion> questions;

    private List<AndroidItemContent> questionContents;

    private List<AndroidItemContent> transcriptContents;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public List<AndroidToeicQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AndroidToeicQuestion> questions) {
        this.questions = questions;
    }

    public List<AndroidItemContent> getQuestionContents() {
        return questionContents;
    }

    public void setQuestionContents(List<AndroidItemContent> questionContents) {
        this.questionContents = questionContents;
    }

    public List<AndroidItemContent> getTranscriptContents() {
        return transcriptContents;
    }

    public void setTranscriptContents(List<AndroidItemContent> transcriptContents) {
        this.transcriptContents = transcriptContents;
    }


    public AndroidToeicQuestionGroup(ToeicQuestionGroup entity) {
        this.serverId = entity.getId();
    }
}
