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
}
