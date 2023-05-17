package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.Statistic;
import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;

import java.util.List;

@Dao
public interface StatisticDao extends ToeicDao<Statistic> {
    @Query("SELECT * FROM Statistic")
    List<Statistic> getAll();

    @Query("SELECT * FROM Statistic where id = :id")
    Statistic getOne(Integer id);
}
