package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;

import java.util.List;

@Dao
public interface ToeicVocabularyDao extends ToeicDao<ToeicVocabulary> {
    @Query("SELECT * FROM ToeicVocabulary")
    List<ToeicVocabulary> getAll();

    @Query("SELECT * FROM ToeicVocabulary INNER JOIN ToeicVocabularyTopic ON ToeicVocabularyTopic.id = ToeicVocabulary.topicId WHERE ToeicVocabularyTopic.name = :topicName")
    List<ToeicVocabulary> getByTopicName(String topicName);

    @Query("SELECT * FROM ToeicVocabulary WHERE id = :id")
    ToeicVocabulary getOne(Integer id);

    @Query("SELECT * FROM ToeicVocabulary INNER JOIN ToeicVocabularyTopic ON ToeicVocabularyTopic.id = ToeicVocabulary.topicId WHERE ToeicVocabularyTopic.id = :id")
    List<ToeicVocabulary> getByTopicId(int id);
}
