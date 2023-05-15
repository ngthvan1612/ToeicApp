package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabGroup;

import java.util.List;

@Dao
public interface FavoriteVocabGroupDao extends ToeicDao<FavoriteVocabGroup> {
    @Query("SELECT * FROM FavoriteVocabGroup")
    List<FavoriteVocabGroup> getAll();

    @Query("SELECT * FROM FavoriteVocabGroup where id = :id")
    FavoriteVocabGroup getOne(Integer id);
}
