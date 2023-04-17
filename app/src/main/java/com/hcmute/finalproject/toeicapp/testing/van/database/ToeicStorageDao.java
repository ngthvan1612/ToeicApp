package com.hcmute.finalproject.toeicapp.testing.van.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

import java.util.List;

@Dao
public interface ToeicStorageDao {
    @Insert
    void insert(ToeicStorage... toeicStorages);

    @Query("SELECT * FROM toeicstorage")
    List<ToeicStorage> listAll();

    @Update
    void update(ToeicStorage... toeicStorages);

    @Delete
    void delete(ToeicStorage toeicStorage);
}
