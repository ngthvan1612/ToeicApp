package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;

import java.util.List;

public interface ToeicVocabularyTopicDao extends ToeicDao<ToeicVocabularyTopic> {
    @Query("SELECT * FROM ToeicVocabularyTopic")
    List<ToeicVocabularyTopic> getAll();

    @Query("SELECT * FROM ToeicVocabularyTopic WHERE id = :id")
    ToeicVocabularyTopic getOne(Integer id);
}
