package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.hcmute.finalproject.toeicapp.entities.ToeicPart;

import java.util.List;

@Dao
public interface ToeicPartDao extends ToeicDao<ToeicPart> {
    @Query("SELECT * FROM ToeicPart")
    List<ToeicPart> getAll();

    @Query("SELECT * FROM ToeicPart WHERE ToeicPart.partNumber = :partNumber")
    List<ToeicPart> listPartsByPartNumber(Integer partNumber);

    @Query("SELECT * FROM ToeicPart WHERE id = :id")
    ToeicPart getOne(Integer id);

    @Query("SELECT * FROM ToeicPart WHERE ToeicPart.serverId = :serverId")
    ToeicPart getToeicPartByServerId(Integer serverId);

    @Query("SELECT * FROM ToeicPart WHERE ToeicPart.toeicFullTestId = :testId")
    List<ToeicPart> getToeicPartByToeicTestId(Integer testId);

    @Query("SELECT * FROM ToeicPart WHERE ToeicPart.toeicFullTestId = :testId" +
    " and (ToeicPart.id = 1 or ToeicPart.id = 2 or ToeicPart.id = 3 or Toeicpart.id = 4)")
    List<ToeicPart> getToeicPartListeningByToeicTestId(Integer testId);

    @Query("SELECT * FROM ToeicPart WHERE ToeicPart.toeicFullTestId = :testId" +
            " and (ToeicPart.id = 5 or ToeicPart.id = 6 or ToeicPart.id = 7)")
    List<ToeicPart> getToeicPartReadingByToeicTestId(Integer testId);
}
