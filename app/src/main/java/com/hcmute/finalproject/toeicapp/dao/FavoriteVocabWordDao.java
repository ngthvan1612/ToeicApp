package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabGroup;
import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabWord;

import java.util.List;

@Dao
public interface FavoriteVocabWordDao extends ToeicDao<FavoriteVocabWord> {
    @Query("SELECT * FROM FavoriteVocabWord")
    List<FavoriteVocabWord> getAll();

    @Query("SELECT * FROM FavoriteVocabWord where id = :id")
    FavoriteVocabWord getOne(Integer id);
}
