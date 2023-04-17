package com.hcmute.finalproject.toeicapp.testing.van.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

import java.util.List;

@Dao
public interface TestToeicStorageDao {
    @Insert
    void insert(ToeicStorage... toeicStorages);

    @Query("SELECT * FROM toeicstorage")
    List<ToeicStorage> listAll();

    @Query("SELECT * FROM toeicstorage WHERE id = :id")
    ToeicStorage getOne(Integer id);

    @Update
    void update(ToeicStorage... toeicStorages);

    @Delete
    void delete(ToeicStorage... toeicStorages);
}
