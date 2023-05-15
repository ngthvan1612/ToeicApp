package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

import java.util.List;

@Dao
public interface ToeicQuestionGroupDao extends ToeicDao<ToeicQuestionGroup> {
    @Query("SELECT * FROM ToeicQuestionGroup")
    List<ToeicQuestionGroup> getAll();

    @Query("SELECT * FROM ToeicQuestionGroup WHERE id = :id")
    ToeicQuestionGroup getOne(Integer id);

    @Query("SELECT * FROM ToeicQuestionGroup a WHERE a.toeicPartId = :id")
    List<ToeicQuestionGroup> getGroupsByPartId(Integer id);
}
