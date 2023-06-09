package com.hcmute.finalproject.toeicapp.model.toeic;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class TestToeicQuestion implements Serializable {
    @SerializedName("question_id")
    private Integer questionId;
    private String question;
    private List<TestToeicAnswerChoice> choices;
    @SerializedName("correct_answer")
    private String correctAnswer;
    private String explain;
    private File resourceDirectory;

    public TestToeicQuestion() {

    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<TestToeicAnswerChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<TestToeicAnswerChoice> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public File getResourceDirectory() {
        return resourceDirectory;
    }

    public void setResourceDirectory(File resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
    }
}
