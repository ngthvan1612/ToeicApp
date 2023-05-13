package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;

import java.util.List;

@Dao
public interface ToeicQuestionDao extends ToeicDao<ToeicQuestion> {
    @Query("SELECT * FROM ToeicQuestion")
    List<ToeicQuestion> getAll();

    @Query("SELECT * FROM ToeicQuestion WHERE id = :id")
    ToeicQuestion getOne(Integer id);

    @Query("SELECT * FROM ToeicQuestion WHERE id = :questionGroupId")
    List<ToeicQuestion> getToeicQuestionByQuestionGroppId(Integer questionGroupId);
}
