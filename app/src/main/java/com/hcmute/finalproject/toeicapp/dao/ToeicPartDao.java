package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

@Dao
public interface ToeicPartDao extends ToeicDao<ToeicPart> {
    @Query("SELECT * FROM ToeicPart")
    List<ToeicPart> getAll();

    @Query("SELECT * FROM ToeicPart WHERE id = :id")
    ToeicPart getOne(Integer id);
}
