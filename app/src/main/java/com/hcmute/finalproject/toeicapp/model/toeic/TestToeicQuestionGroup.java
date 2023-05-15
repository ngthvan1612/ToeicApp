package com.hcmute.finalproject.toeicapp.model.toeic;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class TestToeicQuestionGroup implements Serializable {
    private String type;
    @SerializedName("question_content")
    private List<TestToeicItemContent> questionContent;
    private String audio;
    private List<TestToeicItemContent> transcript;
    private List<TestToeicQuestion> questions;
    private File questionGroupDirectory;

    public TestToeicQuestionGroup() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TestToeicItemContent> getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(List<TestToeicItemContent> questionContent) {
        this.questionContent = questionContent;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public List<TestToeicItemContent> getTranscript() {
        return transcript;
    }

    public void setTranscript(List<TestToeicItemContent> transcript) {
        this.transcript = transcript;
    }

    public List<TestToeicQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TestToeicQuestion> questions) {
        this.questions = questions;
    }

    public File getQuestionGroupDirectory() {
        return questionGroupDirectory;
    }

    public void setQuestionGroupDirectory(File questionGroupDirectory) {
        this.questionGroupDirectory = questionGroupDirectory;
    }
}
