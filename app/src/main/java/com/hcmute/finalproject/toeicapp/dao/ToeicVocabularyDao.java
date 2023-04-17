package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;

import java.util.List;

public interface ToeicVocabularyDao extends ToeicDao<ToeicVocabulary> {
    @Query("SELECT * FROM ToeicVocabulary")
    List<ToeicVocabulary> getAll();

    @Query("SELECT * FROM ToeicVocabulary WHERE id = :id")
    ToeicVocabulary getOne(Integer id);
}
