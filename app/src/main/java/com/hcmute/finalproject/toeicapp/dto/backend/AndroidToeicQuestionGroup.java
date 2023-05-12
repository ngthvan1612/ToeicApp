package com.hcmute.finalproject.toeicapp.dto.backend;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import java.util.List;

public class AndroidToeicQuestionGroup {
    private Integer serverId;

    private List<AndroidToeicQuestion> questions;

    private List<AndroidItemContent> questionContents;

    private List<AndroidItemContent> transcriptContents;

    public AndroidToeicQuestionGroup(ToeicQuestionGroup entity) {
        this.serverId = entity.getId();
    }
}
