package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

import java.util.List;

@Dao
public interface ToeicStorageDao extends ToeicDao<ToeicStorage> {
    @Query("SELECT * FROM ToeicStorage")
    List<ToeicStorage> getAll();

    @Query("SELECT * FROM ToeicStorage WHERE id = :id")
    ToeicStorage getOne(Integer id);
}
