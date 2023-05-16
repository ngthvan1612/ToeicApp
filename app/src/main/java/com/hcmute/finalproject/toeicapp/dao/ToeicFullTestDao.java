package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

@Dao
public interface ToeicFullTestDao extends ToeicDao<ToeicFullTest>{
    @Query("SELECT * FROM ToeicFullTest")
    List<ToeicFullTest> getAll();

    @Query("SELECT * FROM ToeicFullTest where id = :id")
    ToeicFullTest getOne(Integer id);

    @Query("SELECT * FROM ToeicFullTest")
    List<ToeicFullTest> listFullTest();
}
