package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;

import java.util.List;

@Dao
public interface ToeicAnswerChoiceDao extends ToeicDao<ToeicAnswerChoice>{
    @Query("SELECT * FROM ToeicAnswerChoice")
    List<ToeicAnswerChoice> getAll();

    @Query("SELECT * FROM ToeicAnswerChoice where id=:id")
    ToeicAnswerChoice getOne(Integer id);
}
