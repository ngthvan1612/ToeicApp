package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;

import java.util.List;

@Dao
public interface ToeicItemContentDao extends ToeicDao<ToeicItemContent> {
    @Query("SELECT * FROM ToeicItemContent")
    List<ToeicItemContent> getAll();

    @Query("SELECT * FROM ToeicItemContent WHERE id = :id")
    ToeicItemContent getOne(Integer id);

    @Query("SELECT * FROM ToeicItemContent a WHERE a.toeicQuestionGroupEntityQuestionContentId = :groupId" +
            " or a.toeicQuestionGroupEntityTranscriptId = :groupId")
    List<ToeicItemContent> getItemContentByGroupId(Integer groupId);

    @Query("SELECT * FROM ToeicItemContent a WHERE a.toeicQuestionGroupEntityQuestionContentId = :groupId")
    List<ToeicItemContent> getContentByGroupId(Integer groupId);

    @Query("SELECT * FROM ToeicItemContent a WHERE a.toeicQuestionGroupEntityTranscriptId = :groupId")
    List<ToeicItemContent> getTranscriptByGroupId(Integer groupId);
}
